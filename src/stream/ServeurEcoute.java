package stream;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServeurEcoute extends Thread {
	private ArrayList<String> messagesRecus;
    private ServerSocket listenSocket;

	
	ServeurEcoute(int port) {
		messagesRecus = new ArrayList<String>();
		try {
			listenSocket = new ServerSocket(port);
		} catch(IOException ioe) {
			System.err.println(ioe);
		}
	}
	
	public void run() {
		try {
			Socket clientSocket = listenSocket.accept();
			System.out.println("Connexion from:" + clientSocket.getInetAddress());
			BufferedReader socIn = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
			
			while(true) {
				String ln = socIn.readLine();
				messagesRecus.add(ln);
				System.out.println(ln);
			}
		} catch(Exception e) {
			System.err.println(e);
		}
	}
	
	public String getMessage() {
		if(messagesRecus.isEmpty()) {
			return null;
		} else {
			return this.messagesRecus.remove(0);
		}
	}
	
}