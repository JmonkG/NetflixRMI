package rmi.netflix.server;
import java.io.Serializable;
@SuppressWarnings("serial")

public class Message implements Serializable{

	private MessageType _type;
	private int _serverelected;
	private int _serverid;
	private int _numclients;
	public enum MessageType {
			ELECTION, ELECTED
	}
	
	public Message(MessageType type, int elected, int serverid,int numclients) {
		this._type = type;
		this._serverelected = elected;
		this.set_serverid(serverid);
		this._numclients = numclients;
	}
	
	public Message() { }
	
	public MessageType getType() {
		return this._type;
	}
	
	public void setType(MessageType type) {
		this._type = type;
	}
	
	public int getElected() {
		return this._serverelected;
	}
	
	public void setElected(int elected) {
		this._serverelected = elected;
	}
	
	public int getNumClients() {
		return this._numclients;
	}
	
	public void setNumClients(int numclients) {
		this._numclients = numclients;
	}

	public int get_serverid() {
		return _serverid;
	}

	public void set_serverid(int serverid) {
		this._serverid = serverid;
	}
	
}