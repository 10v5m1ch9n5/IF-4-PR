package stream;

import java.io.*;
import java.net.*;

public class ClientPrintThread extends Thread {
	private Socket clientSocket;
	
	ClientWriteThread(Socket s) {
		clientSocket = s;
	}
	
	public void run() {
		try {
			socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String line;
			while(true) {
				System.out.println("recu: " + socIn.readLine);
			}
				
			
		} catch(Exception e) {
			System.err.println(e);
		}
	}

}