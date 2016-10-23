package rmi.netflix.client;

import rmi.netflix.network.*;
import rmi.netflix.server.IServer;
import rmi.netflix.server.Server;

import java.rmi.*;

public class ClientMain {
	
public static void main(String[] args) {
		
		/*if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}*/
		int IdClient = 0;
		String registry = "localhost";
		
		try {
			if (args.length >= 1) {
				IdClient = Integer.parseInt(args[0]);
			
				if (args.length == 2) {
					registry = args[1];
				}
			} else {
				System.out.println("Usage: java RunProcess <IdClient> <?confHost>");
				System.exit(1);
			}
		}catch (NumberFormatException nfe){
			System.out.println("Usage: java RunProcess <IdClient> <?confHost>\n <IdClient> is a number");
			System.exit(1);
		}
		
		Client client1;
		
		
		try{
			client1 = new Client(IdClient);
			
			String registration = "rmi://localhost/oNet";
			//network sends the server
			INetwork iNet = (INetwork) Naming.lookup(registration);

			iNet.registerClient(client1);
			//IServer is associated with client, later on to stream the video the Iserver associated with client1 should be used
			//like IServer iserver = client1.get_server()
			
			
			System.out.println("You are Connected");
			//Register client


		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
}


