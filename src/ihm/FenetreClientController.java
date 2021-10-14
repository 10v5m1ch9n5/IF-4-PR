package ihm;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FenetreClientController {

    @FXML
    private Button SendMessagesButton;

    @FXML
    private VBox chatClientExample;

    @FXML
    private Text chatroomText;

    @FXML
    private Text chatroomVariable;

    @FXML
    private TextArea recievedMessagesTextbox;

    @FXML
    private TextField sendMessagesTextbox;

    @FXML
    private Text userNameText;

    @FXML
    private Text userNameVariable;

    @FXML
    protected void sendMessage() {
        String message = sendMessagesTextbox.getText();
        sendMessagesTextbox.setText("");
        recievedMessagesTextbox.appendText(message + "\n");
    }

}
