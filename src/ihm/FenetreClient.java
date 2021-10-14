package ihm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FenetreClient extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlloader = new FXMLLoader(FenetreClient.class.getResource("FenetreCLient.fxml"));
        Scene scene = new Scene(fxmlloader.load());

        stage.setTitle("ChatClient");
        stage.setScene(scene);
        stage.show();
    }
}
