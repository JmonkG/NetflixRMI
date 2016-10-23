package rmi.netflix.server;
import java.rmi.*;
//import java.lang.Thread;

import rmi.netflix.network.INetwork;


public class ServerMain {
	
	public static void main(String[] args) {
		
		int IdServer = 0;
		String registry = "localhost";

		try {
			if (args.length >= 1) {
				IdServer = Integer.parseInt(args[0]);
			
				if (args.length == 2) {
					registry = args[1];
				}
			} else {
				System.out.println("Usage: java RunProcess <IdServer> <?confHost>");
				System.exit(1);
			}
		}catch (NumberFormatException nfe){
			System.out.println("Usage: java RunProcess <IdServer> <?confHost>\n <IdServer> is a number");
			System.exit(1);
		}
		
		Server serv1;

		//System.out.println("Starting process "+ IdServer);
	
		try{
			serv1 = new Server(IdServer);
			
			//String registration = "rmi://"+ registry + "/Network";
			String registration = "rmi://localhost/oNet";

			INetwork iNet = (INetwork) Naming.lookup(registration);

			System.out.println("registrando el server"+serv1._serverid);
			iNet.registerServer(serv1);
			System.out.println("I am server:"+ serv1._serverid+" and have:"+serv1._numclients+"clients");

			//Thread p1Thread = new Thread(p1);
			//p1Thread.start();

		}catch(Exception e){
			e.printStackTrace();
		}
	
	}


}
