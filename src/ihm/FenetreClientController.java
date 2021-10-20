package ihm;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import stream.EchoClient;

public class FenetreClientController {

    private EchoClient echoCLient = new EchoClient();

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
    private Button chatroomIDSubmitButton;

    @FXML
    private TextField chatroomIDTextField;

    @FXML
    private Text enterChatroomIDText;

    @FXML
    protected void submitUsername() {
        userNameVariable.setText(usernameTextField.getText());

        enterUsernameText.setVisible(false);
        welcomeToChatClientText.setVisible(false);
        usernameTextField.setVisible(false);
        usernameSubmitButton.setVisible(false);

        chatroomIDSubmitButton.setVisible(true);
        chatroomIDTextField.setVisible(true);
        enterChatroomIDText.setVisible(true);

        FenetreClient.sendMessage(userNameVariable.getText());
    }

    @FXML
    protected void submitChatroomID() {
        chatroomVariable.setText(chatroomIDTextField.getText());

        chatroomIDSubmitButton.setVisible(false);
        chatroomIDTextField.setVisible(false);
        enterChatroomIDText.setVisible(false);

        sendMessagesButton.setVisible(true);
        chatClientExample.setVisible(true);
        chatroomText.setVisible(true);
        chatroomVariable.setVisible(true);
        recievedMessagesTextbox.setVisible(true);
        sendMessagesTextbox.setVisible(true);
        userNameText.setVisible(true);
        userNameVariable.setVisible(true);

        FenetreClient.sendMessage(chatroomVariable.getText());
        FenetreClient.startListen();

        recievedMessagesTextbox.setText("");
    }

    @FXML
    protected void sendMessage() {
        String message = sendMessagesTextbox.getText();
        if(!message.equals("")) {
            sendMessagesTextbox.setText("");
            recievedMessagesTextbox.appendText( userNameVariable.getText() + ": "+ message + "\n");
            FenetreClient.sendMessage(userNameVariable.getText() + ": "+ message + "\n");
        }
    }

    public void recieveMessage(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                recievedMessagesTextbox.appendText( message + "\n");
            }
        });
    }

}
