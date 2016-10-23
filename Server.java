package rmi.netflix.server;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;


import rmi.netflix.client.IClient;
import rmi.netflix.server.Message;

@SuppressWarnings("serial")

public class Server extends UnicastRemoteObject implements IServer, Runnable {
	LinkedList<IClient> _clients;
	public enum ElectionState {
		NON_PARTICIPANT, PARTICIPANT
	}
	IServer _nextServer;
	int _serverid;
	ElectionState _status;
	int _elected_server;
	boolean _startElec;
	int _numclients;
	private float load;
	
	public Server(int serverid) throws RemoteException {
		super();
		this._serverid = serverid;
		this._numclients = 0;
		this.initialize();
		this._clients=new LinkedList<IClient>();
		this.setLoad(0);
	}
	
	private void initialize() {
		System.out.println("Initializing Server "+this._serverid);
		this._nextServer = null;
		this._status = ElectionState.NON_PARTICIPANT;
		_elected_server = -1;
	}
	
	public void printServer() throws RemoteException {
		System.out.println("The server id is"+ this._serverid);
		System.out.println("The server client no. is:"+ this._numclients);
		System.out.println("The next server has the id:"+ this._nextServer.getServerID());
		
	}
	public int getNext_ServerID() throws RemoteException{
		return this._nextServer.getServerID();
	}
	public void printMsg(String msg) {
		System.out.println("<Server "+ this._serverid +"> "+ msg);
	}
	
	public void setStartElection(boolean start) {
		this._startElec = start;
	}
	@Override
	public int getNumClients() throws RemoteException{
		return _numclients;
	}
	@Override
	public void setNewClient(IClient client) throws java.rmi.RemoteException{
		this._numclients+=1;
		this._clients.add(client);
		System.out.println("Client with ID" +client.get_clientid() + " is now my client.\nI have"+ this._numclients + "clients now");
	}
		
	@Override
	public int getServerID() throws RemoteException {
		// TODO Auto-generated method stub
		return _serverid;
	}

	@Override
	public void setNextServer(IServer next_server) throws RemoteException {
		this._nextServer = next_server;
		printMsg("Next Process is: "+ this._nextServer.getServerID());
	}

	@Override
	public void receiveMsg(Message aMsg) throws RemoteException {
		this.procMessage(aMsg);
	}
	
	public void sendMessage(Message aMsg) throws RemoteException {
		if (this._nextServer != null)
			this._nextServer.receiveMsg(aMsg);
	}
	
	public void forwardMessage(Message aMsg) throws RemoteException {
		this.sendMessage(aMsg);
		this._status = ElectionState.PARTICIPANT;
	}
	
	public void electionCompleted() {		
		printMsg("Elected process: "+ _elected_server);
		if(this._serverid == this._elected_server)
			System.out.println("I have been elected");
					
	}
	
	public void procMessage(Message aMsg) throws RemoteException {
		Message.MessageType msgType = aMsg.getType();
		//boolean recvGreater = false;
		
		if (msgType == Message.MessageType.ELECTION) {
			
			if (_serverid != aMsg.get_serverid()) {
				if (_numclients < aMsg.getNumClients()) {
					aMsg.setNumClients(_numclients);
					aMsg.set_serverid(_serverid);
					//} else {
					//recvGreater = true;
				//}
				//if (_status == ElectionState.NON_PARTICIPANT || (_status == ElectionState.PARTICIPANT && recvGreater)) {
					//forwardMessage(aMsg);
				}
				forwardMessage(aMsg);
			} else {
				Message aElected = new Message(Message.MessageType.ELECTED,_serverid ,_serverid, _numclients);
				_status = ElectionState.NON_PARTICIPANT;
				sendMessage(aElected);				
			}
		} else if (msgType == Message.MessageType.ELECTED) {
			_status = ElectionState.NON_PARTICIPANT;
			
			_elected_server = aMsg.getElected();
			
			if (_elected_server != _serverid) {
				sendMessage(aMsg);
			} 
			
			electionCompleted();
		}
	}
	
	public void detectStart() throws RemoteException {
		if (this._startElec && this._nextServer != null) {
			printMsg("Detected Start");
			Message aMsg = new Message(Message.MessageType.ELECTION, -1, _serverid,_numclients);
			forwardMessage(aMsg);
			_startElec = false;
		}
	}
	@Override
	public void startElection() throws RemoteException {
		printMsg("Starting Election");
		Message aMsg = new Message(Message.MessageType.ELECTION, -1, _serverid,_numclients);
		forwardMessage(aMsg);
	}
	
	public int getElectedServer() throws RemoteException{
		return this._elected_server;
	}
		
	public void removeClient(IClient client) throws RemoteException{
		this._numclients-=1;
		this._clients.remove(client);
		System.out.println("Client with ID" +client.get_clientid() + " removed.\nI have"+ this._numclients + "clients now");
	}
	
	
	public void run() {
		
		while (true);
		
	}

	public float getLoad() throws java.rmi.RemoteException{
		return load;
	}

	public void setLoad(float load) {
		this.load = load;
	}

}
