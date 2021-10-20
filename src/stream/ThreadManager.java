package stream;

import java.io.*;
import java.net.*;
import java.util.*;

public class ThreadManager {
	private ArrayList<String> pendingMessages;
	private HashMap<String,Socket> utilisateurs;
	private HashMap<String,String> salles;
	private HashMap<String, String> anciensMessages;
	final static String filepath = "src/stream/anciensMessages.txt";

	ThreadManager() throws IOException {
		pendingMessages = new ArrayList<>();
		utilisateurs = new HashMap<>();
		salles = new HashMap<>();
		anciensMessages = new HashMap<>();
		anciensMessages = reloadMessagesFromFile();

	}

	/**
	 * loads messages that already have been sent before a user entered a chatroom
	 * @param idSalle the id of the chatroom
	 **/
	public String getAncienMessages(String idSalle) {
		return anciensMessages.get(idSalle);
	}

	/**
	 * forwarding sent message to other users in the same chatroom
	 * @param message that is sent
	 * @param username of the sender
	 **/
	public void write(String message, String username) throws IOException {
		//store message in HashMap ancienMessages with id of chatroom as key
		if (!(anciensMessages.get(salles.get(username)) == null)) {
			String text = (anciensMessages.get(salles.get(username)));
			text += message + "\n";
			anciensMessages.replace(salles.get(username),text);
		}
		else {
			String text = message + "\n";
			anciensMessages.put(salles.get(username),text);
		}
		saveMessagesInFile(anciensMessages);

		pendingMessages.add(message);

		// forward message to other users in the same chatroom
		try {
			for(String usr : utilisateurs.keySet()) {
				if(Objects.equals(usr, username)) continue;
				if (!salles.get(username).equals(salles.get(usr))) continue;
				System.out.println("Envoi à " + usr);
				PrintStream socOut = new PrintStream(utilisateurs.get(usr).getOutputStream());
				socOut.println(message);
			}
		} catch(Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * store send messages in file for persistence
	 * @param anciensMessages the Map where the messages are stored with id of chatroom as key
	 **/
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

	/**
	 * reloads old messages from file into HashMap ancien messages
	 * @return anciensMessages the HashMap of already sent messages with chatroom id as key
	 **/
	private HashMap<String, String> reloadMessagesFromFile() throws IOException {
		File file = new File(filepath);
		BufferedReader bufferdReader = new BufferedReader(new FileReader(file));

		String line;
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
	
	public void addUser(Socket s, String username, String idSalle) {
		utilisateurs.put(username, s);
		salles.put(username,idSalle);
	}
	
	public void rmUser(String username) {
		utilisateurs.remove(username);
		salles.remove(username);
	}
}
