/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;
import java.util.*;

public class EchoServerMultiThreaded  {
  
 	/**
  	* main method
	* @param EchoServer port
  	* 
  	**/
       public static void main(String args[]){ 
        //ServerSocket listenSocket;
		ArrayList<ClientThread> users = new ArrayList<ClientThread>();
        
  	if (args.length != 1) {
          System.out.println("Usage: java EchoServer <EchoServer port>");
          System.exit(1);
  	}
	try {
		//listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
		ServeurEcoute se = new ServeurEcoute(Integer.parseInt(args[0]));
		se.start();
		System.out.println("Server ready...");
		
		while (true) {
			/* Socket clientSocket = listenSocket.accept();
			System.out.println("Connexion from:" + clientSocket.getInetAddress());
			
			BufferedReader socIn = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
			String ln = socIn.readLine();
			if(ln.split(" ")[0].equals("username")) {
				System.out.println(ln.split(" ")[1] + " s'est connecté(e).");
				
				ClientThread ct = new ClientThread(clientSocket, ln.split(" ")[1]);
				ct.start();
				users.add(ct);
				
				System.out.println("Liste des utilisateurs connectés :");
				for(int i = 0; i < users.size(); ++i) {
					System.out.println(users.get(i).username);
				}
			} else if(ln.split(" ")[0].equals("salon")) {
				System.out.println(ln.split(" ")[1]);
			} else {
				System.out.println("ln : " + ln);
				for(int i = 0; i < users.size(); ++i) {
					System.out.println("récupération des messages...");
					if(users.get(i).messages.size() != 0)
						System.out.println(users.get(i).messages.remove(0));
				}
			} */
			
		}
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
      }
  }

  
