package ihm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import stream.ClientPrintThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import stream.ClientPrintThread;

public class FenetreClient extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlloader = new FXMLLoader(FenetreClient.class.getResource("FenetreCLient.fxml"));
        Scene scene = new Scene(fxmlloader.load());

        stage.setTitle("ChatClient");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        Socket echoSocket = null;
        PrintStream socOut = null;
        BufferedReader stdIn = null;

        if (args.length != 2) {
            System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
            System.exit(1);
        }

        try {
            // creation socket ==> connexion
            echoSocket = new Socket(args[0],new Integer(args[1]).intValue());
            socOut= new PrintStream(echoSocket.getOutputStream());
            stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + "the connection to:"+ args[0]);
            System.exit(1);
        }

        String line;
        ClientPrintThread cpt = new ClientPrintThread(echoSocket);
        cpt.start();

        while (true) {
            line=stdIn.readLine();
            socOut.println(line);
            if (line.equals(".")) break;
        }
        socOut.close();
        stdIn.close();
        echoSocket.close();

        launch();
    }
}
