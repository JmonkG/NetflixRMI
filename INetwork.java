package rmi.netflix.network;
import java.rmi.Remote;

import rmi.netflix.server.IServer;

public interface INetwork extends Remote {
	
	public void registerServer(IServer serv) throws java.rmi.RemoteException;

}
