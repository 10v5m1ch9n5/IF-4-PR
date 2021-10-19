/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;

public class ClientThread extends Thread {
	
	private Socket clientSocket;
	private ThreadManager tm;
	private String username;
	private String idSalle;
	
	
	ClientThread(Socket s, ThreadManager tm) {
		this.clientSocket = s;
		this.tm = tm;
	}

 	/**
  	* receives a request from client then sends an echo to the client
  	* @param clientSocket the client socket
  	**/
	public void run() {
    	try {
    		BufferedReader socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
			socOut.println("Entrez votre nom d'utilisateur :");
			username = socIn.readLine();
			socOut.println("Bienvenue, " + username + ".");
			socOut.println("Entrez votre idSalle :");
			idSalle = socIn.readLine();
			tm.addUser(clientSocket, username, idSalle);
			if (!(tm.getAncienMessages(idSalle) == null)) {
				socOut.println("\n OLD MESSAGES:");
				socOut.println(tm.getAncienMessages(idSalle));
				socOut.println("\n \n NEW MESSAGES:");
			}

    		while (true) {
    		    String line = socIn.readLine();
			    if (line.equals(".")) { tm.rmUser(username); break; }
			    tm.write(line, username);
    		}
			socIn.close();
			clientSocket.close();
    	} catch (Exception e) {
        	System.err.println("Error in EchoServer:" + e); 
        }
    }
  
}

  
