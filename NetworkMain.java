package rmi.netflix.network;

import java.net.MalformedURLException;
import java.rmi.*;
//import java.lang.Thread;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

public class NetworkMain{
	
	public static void main(String[] args) throws RemoteException, MalformedURLException {
			
			int MaxNumServers = 0;
			
			try {
				if (args.length >= 1) {
					MaxNumServers = Integer.parseInt(args[0]);
				} else {
					System.out.println("Usage: java NetworkMain <numOfProcesses>");
					System.exit(1);
				}
			} catch(NumberFormatException nfe) {
				System.out.println("Usage: java NetworkMain <numOfProcesses>\n<numOfProcesses> is a number.");
				System.exit(1);
			}
	
			System.out.println("Running Network Topology Configurator\n");
			
			try {
				Network oNet = new Network(MaxNumServers);
				System.out.println("Creating the Network");
				Registry registry = LocateRegistry.createRegistry(1099);
				//String registry = "localhost:1099";
				
				//if (args.length >= 2) {
				//	registry = args[1];
				//}
				
				//String regUrl = "rmi://"+ registry +"/Network";
				Naming.rebind("rmi://localhost/oNet", oNet);
				//Naming.rebind(regUrl, oNet);
				
				//Thread oNetThread = new Thread(oNet);
				System.out.println("Network Sucessully created!");
				oNet.run();
				//oNetThread.start();
				oNet.get_ElectedServer();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

}
