package rmi.netflix.network;
import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.netflix.client.IClient;
import rmi.netflix.server.IServer;

public interface INetwork extends Remote {
	
	public void registerServer(IServer serv) throws java.rmi.RemoteException;
	public void registerClient(IClient client) throws java.rmi.RemoteException;

}
