package stream;

import ihm.FenetreClient;
import javafx.application.Application;

import java.io.*;
import java.net.*;
import java.util.Observable;
import java.util.Observer;

public class ClientPrintThread extends Observable implements Runnable {
	private Socket clientSocket;
	private Boolean recording;
	
	public ClientPrintThread(Socket s, FenetreClient app) {
		addObserver(app);
		clientSocket = s;
		recording = false;
	}

	public void enabledrecord() {
		recording = true;
	}
	
	public void run() {
		try {
			BufferedReader socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String line;
			while(true) {
				line = socIn.readLine();
				System.out.println(line);
				if (recording && !line.equals("")) {
					setChanged();
					notifyObservers(line);
				}
			}
		} catch(Exception e) {
			System.err.println(e);
		}
	}

}