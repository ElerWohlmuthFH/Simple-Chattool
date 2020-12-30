package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.model.PopupWindow;
import at.ac.fhcampuswien.thread.ReceiveMessageThread;
import at.ac.fhcampuswien.thread.ReceiveMessageThreadForServer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ServerChatController implements Initializable {
    private String previousMessage;
    @FXML
    private Button btnSend;

    @FXML
    private TextArea textFieldArea;

    @FXML
    private TextField textFieldMessages;


    @FXML
    void btnSendAction(ActionEvent event) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(ServerStartController.socket.getOutputStream());
        String messageToSend = textFieldMessages.getText();
        outputStream.writeUTF(messageToSend);
        outputStream.flush();

        textFieldArea.appendText("Server: "+ messageToSend + "\n");
        textFieldMessages.clear();
    }
    public void timer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (ReceiveMessageThreadForServer.message != null && !ReceiveMessageThreadForServer.message.equals(previousMessage)) {
                    appendText();
                    previousMessage = ReceiveMessageThreadForServer.message;
                }
            }
        }, 1000, 1000);
    }
    public void appendText() {
        textFieldArea.appendText("Client: " + ReceiveMessageThreadForServer.message);
        textFieldArea.appendText("\n");
    }

    @FXML
    void keyReleasedProperty(KeyEvent event) {
        String writeMessage = textFieldMessages.getText();

        boolean btnSendDisabled = (writeMessage.isEmpty() || writeMessage.trim().isEmpty());
        btnSend.setDisable(btnSendDisabled);
    }

    @FXML
    private MenuItem menuDisconnect;
    @FXML
    void disconnectAction(ActionEvent event) {
        PopupWindow.display("Disconnected!");
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFieldArea.setText("Messages:\n");
        timer();
    }
}
