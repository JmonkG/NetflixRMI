package rmi.netflix.client;

import java.rmi.Remote;

import rmi.netflix.server.IServer;

public interface IClient extends Remote{
	
	public IServer get_server() throws java.rmi.RemoteException;;

	public void set_server(IServer _server) throws java.rmi.RemoteException;;
	public int get_clientid() throws java.rmi.RemoteException;;

}
