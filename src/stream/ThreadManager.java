package stream;

import java.io.*;
import java.net.*;
import java.util.*;

public class ThreadManager {
	private ArrayList<String> pendingMessages;
	// private ArrayList<Socket> utilisateurs;
	private HashMap<String,Socket> utilisateurs;
	private HashMap<String,String> salles;
	private HashMap<String, String> anciensMessages;
	final static String filepath = "src/stream/anciensMessages.txt";

	ThreadManager() throws IOException {
		pendingMessages = new ArrayList<String>();
		utilisateurs = new HashMap<String,Socket>();
		salles = new HashMap<String, String>();
		anciensMessages = new HashMap<>();
		anciensMessages = reloadMessagesFromFile();

	}

	private HashMap<String, String> reloadMessagesFromFile() throws IOException {
		HashMap<String, String> reload = new HashMap<>();
		File file = new File(filepath);
		BufferedReader bufferdReader = new BufferedReader(new FileReader(file));

		String line = null;

		while ((line = bufferdReader.readLine()) != null) {
			String[] split = line.split(";");
			String idSalle = split[0].trim();
			String messages = split[1].trim();
			if (!idSalle.equals("") && !messages.equals("")) {
				anciensMessages.put(idSalle,messages.replace("%%%","\n"));
			}
		}
		return anciensMessages;
	}

	public String getAncienMessages(String idSalle) {
		return anciensMessages.get(idSalle);
	}

	public void write(String s, String username) throws IOException {
		if (!(anciensMessages.get(salles.get(username)) == null)) {
			String text = (anciensMessages.get(salles.get(username)));
			text += s + "\n";
			anciensMessages.replace(salles.get(username),text);
		}
		else {
			String text = s + "\n";
			anciensMessages.put(salles.get(username),text);
		}
		saveMessagesInFile(anciensMessages);

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

	private void saveMessagesInFile(HashMap<String, String> anciensMessages) throws IOException {
		File file = new File(filepath);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
		for (Map.Entry<String, String> entry : anciensMessages.entrySet()) {
			bufferedWriter.write(entry.getKey() + ";" + entry.getValue().replace("\n","%%%"));
			bufferedWriter.newLine();
		}
		bufferedWriter.flush();
		bufferedWriter.close();
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
