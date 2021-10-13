/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package stream;

import java.io.*;
import java.net.*;



public class EchoClient {

 
  /**
  *  main method
  *  accepts a connection, receives a message from client then sends an echo to the client
  **/
    public static void main(String[] args) throws IOException {

        Socket echoSocket = null;
        PrintStream socOut = null;
        BufferedReader stdIn = null;
        BufferedReader socIn = null;
		String username = null;
		int salon;

        if (args.length != 2) {
          System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
          System.exit(1);
        }

        try {
      	    // creation socket ==> connexion
      	    echoSocket = new Socket(args[0],new Integer(args[1]).intValue());
	    socIn = new BufferedReader(
	    		          new InputStreamReader(echoSocket.getInputStream()));    
	    socOut= new PrintStream(echoSocket.getOutputStream());
	    stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to:"+ args[0]);
            System.exit(1);
        }
                             
        String line;
        while (true) {
        	line=stdIn.readLine();
        	if (line.equals(".")) break;
			if (line.split(" ")[0].equals("username")) {
				username = line.split(" ")[1];
				System.out.println("Votre username : " + username);
				System.out.println("Coucou " + username);
				socOut.println("username " + username);
			} else if (line.split(" ")[0].equals("salon")) {
				salon = Integer.parseInt(line.split(" ")[1]);
				// socOut.println("salon " + salon);
			} else {
				socOut.println(line);
				//System.out.println("echo: " + socIn.readLine());
			}
        }
      socOut.close();
      socIn.close();
      stdIn.close();
      echoSocket.close();
    }
}


