package rmi.netflix.client;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ThreadLocalRandom;

import rmi.netflix.server.IServer;



@SuppressWarnings("serial")

public class Client extends UnicastRemoteObject implements IClient,Runnable{

	private int _clientid;
	private IServer _server;
	
	public Client (int clientid) throws RemoteException{
		this._clientid=clientid;
		this.set_server(null);
	}

	public IServer get_server() {
		return _server;
	}

	public void set_server(IServer _server) {
		this._server = _server;
	}

	public int get_clientid() {
		return _clientid;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true);
		
	}


	
	
}
