package stream;

import java.io.*;
import java.net.*;
import java.util.*;

public class ThreadManager {
	private ArrayList<String> pendingMessages;
	// private ArrayList<Socket> utilisateurs;
	private HashMap<String,Socket> utilisateurs;
	private HashMap<String,String> salles;
	
	ThreadManager() {
		pendingMessages = new ArrayList<String>();
		utilisateurs = new HashMap<String,Socket>();
		salles = new HashMap<String, String>();
	}
	
	public void write(String s, String username) {
		pendingMessages.add(s);
		try {
			for(String usr : utilisateurs.keySet()) {
				if(Objects.equals(usr, username)) continue;
				if (!salles.get(username).equals(salles.get(usr))) continue;
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
	
	public void addUser(Socket s, String username, String idSalle) {
		utilisateurs.put(username, s);
		salles.put(username,idSalle);
	}
	
	public void rmUser(String username) {
		utilisateurs.remove(username);
		salles.remove(username);
	}
	
	public String getMessage() {
		return pendingMessages.get(pendingMessages.size()-1);
	}
	
}
