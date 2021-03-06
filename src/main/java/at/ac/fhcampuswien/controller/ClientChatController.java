package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.model.PopupWindow;
import at.ac.fhcampuswien.thread.ReceiveMessageThread;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ClientChatController implements Initializable {
    private static String previousMessage;

    @FXML
    private Button btnSend;
    @FXML
    private TextArea textFieldArea;
    @FXML
    private TextField textFieldMessages;
    @FXML
    private MenuItem menuDisconnect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFieldArea.setText("Messages:\n");
        textFieldArea.setWrapText(true);
        btnSend.setDisable(true);
        timer();
    }

    @FXML
    void btnSendAction(ActionEvent event) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(ClientStartController.socket.getOutputStream());
        String messageToSend = textFieldMessages.getText();
        outputStream.writeUTF(messageToSend);
        outputStream.flush();

        textFieldArea.appendText("Client: "+ messageToSend + "\n");
        textFieldArea.setWrapText(true);
        textFieldMessages.clear();
    }

    public void timer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (ReceiveMessageThread.message != null && !ReceiveMessageThread.message.equals(previousMessage)) {
                    appendText();
                    previousMessage = ReceiveMessageThread.message;
                }
            }
        }, 1000, 1000);
    }

    public void appendText() {
        textFieldArea.appendText("Server: " + ReceiveMessageThread.message);
        textFieldArea.appendText("\n");
    }

    @FXML
    void keyReleasedProperty(KeyEvent event) {
        String writeMessage = textFieldMessages.getText();
        boolean btnSendDisabled = (writeMessage.isEmpty() || writeMessage.trim().isEmpty());
        btnSend.setDisable(btnSendDisabled);
    }

    @FXML
    void disconnectAction(ActionEvent event) throws IOException {
        menuDisconnect();
    }

    private void menuDisconnect() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Disconnecting...");
        alert.setHeaderText("You are about to disconnect from Server. \nWould you like to proceed?");

        ButtonType stay = new ButtonType("Stay");
        ButtonType disconnect = new ButtonType("Disconnect");

        alert.getButtonTypes().clear();

        alert.getButtonTypes().addAll(stay, disconnect);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == stay) {
            alert.close();
        } else if (option.get() == disconnect) {
            PopupWindow.display("Disconnected!");
            System.exit(0);
        }
    }
}