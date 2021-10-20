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
import java.util.Observable;
import java.util.Observer;

public class FenetreClient extends Application implements Observer {

    public static Socket echoSocket = null;
    public static PrintStream socOut = null;
    public static BufferedReader stdIn = null;
    public static FenetreClientController controller;
    public static Thread listeningthread;
    public  static ClientPrintThread cpt;

    public static void sendMessage(String message) {
        System.out.println("Message envoyé: " + message);
        socOut.println(message);
    }

    @Override
    public void start(Stage stage) {
        FXMLLoader fxmlloader = new FXMLLoader(FenetreClient.class.getResource("FenetreCLient.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlloader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller = fxmlloader.getController();

        stage.setTitle("ChatClient");
        stage.setScene(scene);
        stage.show();
        cpt = new ClientPrintThread(echoSocket,this);
        listeningthread= new Thread(cpt);
        listeningthread.setDaemon(true);
        listeningthread.start();
    }

    public static void startListen() {
        cpt.enabledrecord();
    }

    public static void main(String[] args) {

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
        launch();
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("msgreceive");
        controller.recieveMessage((String) arg);
    }

    public void exit() {
        System.exit(0);
    }
}
