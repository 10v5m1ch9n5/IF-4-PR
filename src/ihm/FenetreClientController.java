package ihm;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FenetreClientController {

    @FXML
    private Button sendMessagesButton;

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
    private Text enterUsernameText;

    @FXML
    private Text welcomeToChatClientText;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button usernameSubmitButton;

    @FXML
    protected void submitUsername() {
        userNameVariable.setText(usernameTextField.getText());
        sendMessagesButton.setVisible(true);
        chatClientExample.setVisible(true);
        chatroomText.setVisible(true);
        chatroomVariable.setVisible(true);
        recievedMessagesTextbox.setVisible(true);
        sendMessagesTextbox.setVisible(true);
        userNameText.setVisible(true);
        userNameVariable.setVisible(true);
        enterUsernameText.setVisible(false);
        welcomeToChatClientText.setVisible(false);
        usernameTextField.setVisible(false);
        usernameSubmitButton.setVisible(false);
    }

    @FXML
    protected void sendMessage() {
        String message = sendMessagesTextbox.getText();
        sendMessagesTextbox.setText("");
        recievedMessagesTextbox.appendText(message + "\n");
    }

}
