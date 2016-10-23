package rmi.netflix.server;
import java.rmi.Remote;


import rmi.netflix.client.IClient;

public interface IServer extends Remote {

	public void setNextServer(IServer next_server) throws java.rmi.RemoteException;
	
	public int getServerID() throws java.rmi.RemoteException;
	
	public int getNext_ServerID() throws java.rmi.RemoteException;
	
	public int getElectedServer() throws java.rmi.RemoteException;
	
	public int getNumClients() throws java.rmi.RemoteException;
		
	public void printServer() throws java.rmi.RemoteException;
	
	public void setNewClient(IClient client) throws java.rmi.RemoteException;
	
	public void receiveMsg(Message aMsg) throws java.rmi.RemoteException;
	
	public void startElection() throws java.rmi.RemoteException;
	public float getLoad() throws java.rmi.RemoteException;
	
}
