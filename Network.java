package rmi.netflix.network;
import java.util.LinkedList;
import rmi.netflix.server.IServer;
import java.rmi.*;
import java.rmi.server.*;


@SuppressWarnings("serial")
public class Network extends UnicastRemoteObject implements INetwork, Runnable {
	LinkedList<IServer> _vServers;
	int _maxNumServers;
	
	public Network(int maxNumServers) throws RemoteException { 
		super();
		_maxNumServers = maxNumServers;
		_vServers = new LinkedList<IServer>();
	}
	
	public void PrintListServers() throws RemoteException{
		for (int i=0;i<this._vServers.size();i++){
			IServer servprint = this._vServers.get(i);
			System.out.println("The server id is"+ servprint.getServerID());
			System.out.println("The server client no. is:"+ servprint.getNumClients());
			System.out.println("The next server has the id:"+ servprint.getNext_ServerID());
			//servprint.printServer();
		}
	}
	public void get_ElectedServer() throws RemoteException{
		IServer elected;
		for (int i =0;i<this._vServers.size();i++){
			elected = this._vServers.get(i);
			if(elected.getElectedServer() == elected.getServerID()){
				System.out.println("El servidor elegido es:"+elected.getServerID());
				elected.addClient();
			}
		}
			
				
			
	}
	public synchronized int allServersRegistered() {
		if( this._vServers.size() == this._maxNumServers)
			return 1;
		else
			return 0;
	}

	@Override
	public void registerServer(IServer serv) throws java.rmi.RemoteException {
		synchronized (_vServers) {
			if (_vServers.size() != _maxNumServers) {
				System.out.println("Registering server with ServerID: "+ serv.getServerID());
				_vServers.add(serv);
			}
		}
	}
	
	public void run() {
		
		// Wait while all processes connect.
		while (this.allServersRegistered() == 0);
			//System.out.println("Waiting for all servers!. Number of servers added:"+this._vServers.size());
		System.out.println("All servers registered!");
		
		// Once all processes are connected, organize them in a logical ring.
		for (int i=0; i<this._vServers.size(); i++) {
			
			IServer thisServ = _vServers.get(i);
			IServer nextServ;

			if (i != this._vServers.size()-1) {
				nextServ = _vServers.get(i+1);
			} else {
				nextServ = _vServers.getFirst();
			}
			
			try {
				thisServ.setNextServer(nextServ);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		// At this point the topology has been build.
		System.out.println("Network Topology created as a Logical Ring!");
			
		/*try {
			this.PrintListServers();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	*/
		
		// Start election in a random way.
		int iStarter = Math.round((float)((this._maxNumServers-1)*Math.random()));
		System.out.println("valor del iStarter es"+iStarter);
		try {
			(this._vServers.get(iStarter)).startElection();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		//while(true);
	}

}
