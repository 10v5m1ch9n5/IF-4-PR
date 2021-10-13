/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientThread
	extends Thread {
	
	private Socket clientSocket;
	public ArrayList<String> messages = new ArrayList<String>();
	public String username;
	
	ClientThread(Socket s, String username) {
		this.clientSocket = s;
		this.username = username;
	}

 	/**
  	* receives a request from client then sends an echo to the client
  	* @param clientSocket the client socket
  	**/
	public void run() {
    	  try {
    		BufferedReader socIn = null;
    		socIn = new BufferedReader(
    			new InputStreamReader(clientSocket.getInputStream()));    
    		PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
    		while (true) {
    		  String line = socIn.readLine();
			  if(line != "") {
				  messages.add(this.username + ": " + line + " (thread)");
			  }
    		  //socOut.println(line + "thread");
    		}
    	} catch (Exception e) {
        	System.err.println("Error in EchoServer:" + e); 
        }
       }
  
  }

  
