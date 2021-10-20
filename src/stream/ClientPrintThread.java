package stream;

import ihm.FenetreClient;

import java.io.*;
import java.net.*;
import java.util.Observable;

public class ClientPrintThread extends Observable implements Runnable {
	private Socket clientSocket;
	private Boolean recording;
	private FenetreClient app;
	
	public ClientPrintThread(Socket s, FenetreClient app) {
		this.app = app;
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
			System.out.println("Closing ChatClient...");
			try {
				app.exit();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}