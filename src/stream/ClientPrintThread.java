package stream;

import java.io.*;
import java.net.*;

public class ClientPrintThread extends Thread {
	private Socket clientSocket;
	
	ClientPrintThread(Socket s) {
		clientSocket = s;
	}
	
	public void run() {
		try {
			BufferedReader socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String line;
			while(true) {
				System.out.println("recu: " + socIn.readLine());
			}
			//socIn.close();
				
			
		} catch(Exception e) {
			System.err.println(e);
		}
	}

}