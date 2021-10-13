package stream;

import java.io.*;
import java.net.*;
import java.util.*;

public class ThreadManager {
	private ArrayList<String> pendingMessages;
	private ArrayList<Socket> utilisateurs;
	
	ThreadManager() {
		pendingMessages = new ArrayList<String>();
		utilisateurs = new ArrayList<Socket>();
	}
	
	public void write(String s, int id) {
		pendingMessages.add(s);
		try {
			for(int i = 0; i < utilisateurs.size(); i++) {
				if(i == id) continue;
				PrintStream socOut = new PrintStream(utilisateurs.get(i).getOutputStream());
				socOut.println(s);
			}
		} catch(Exception e) {
			System.err.println(e);
		}
	}
	
	public boolean newMessages() {
		return !pendingMessages.isEmpty();
	}
	
	public int addUser(Socket s) {
		utilisateurs.add(s);
		return utilisateurs.size()-1;
	}
	
	public void rmUser(int id) {
		utilisateurs.remove(id);
	}
	
	public String getMessage() {
		return pendingMessages.get(pendingMessages.size()-1);
	}
	
}
