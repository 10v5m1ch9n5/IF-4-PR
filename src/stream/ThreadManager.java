package stream;

import java.io.*;
import java.net.*;
import java.util.*;

public class ThreadManager {
	private ArrayList<String> pendingMessages;
	// private ArrayList<Socket> utilisateurs;
	private HashMap<String,Socket> utilisateurs;
	
	ThreadManager() {
		pendingMessages = new ArrayList<String>();
		utilisateurs = new HashMap<String,Socket>();
	}
	
	public void write(String s, String username) {
		pendingMessages.add(s);
		try {
			for(String usr : utilisateurs.keySet()) {
				if(Objects.equals(usr, username)) continue;
				System.out.println("Envoi à " + usr);
				PrintStream socOut = new PrintStream(utilisateurs.get(usr).getOutputStream());
				socOut.println(s);
			}
		} catch(Exception e) {
			System.err.println(e);
		}
	}
	
	public boolean newMessages() {
		return !pendingMessages.isEmpty();
	}
	
	public void addUser(Socket s, String username) {
		utilisateurs.put(username, s);
	}
	
	public void rmUser(String username) {
		utilisateurs.remove(username);
	}
	
	public String getMessage() {
		return pendingMessages.get(pendingMessages.size()-1);
	}
	
}
