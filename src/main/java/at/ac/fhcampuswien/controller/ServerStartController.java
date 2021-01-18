package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.model.PopupWindow;
import at.ac.fhcampuswien.model.ToolTipWindow;
import at.ac.fhcampuswien.thread.ReceiveMessageThreadForServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerStartController {
    public static ServerSocket serverSocket;
    public static Socket socket;

    @FXML
    private Button btnConnect;
    @FXML
    private TextField textFieldPort;

    @FXML
    void btnConnectAction(ActionEvent event) {
        String portString = textFieldPort.getText();

        try {
            serverSocket = new ServerSocket(Integer.parseInt(portString));
            System.out.println("Ready for connection...");
            socket = serverSocket.accept();

            if (portString.equals(String.valueOf(socket.getLocalPort()))) {
                PopupWindow.display("Connection to Client was successful!");

                Parent root = FXMLLoader.load(getClass().getResource("/ServerChatScreen.fxml"));
                Scene scene = new Scene(root);

                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("- Simple Chat Tool - SERVER -");
                stage.show();

                new ReceiveMessageThreadForServer(socket, ClientChatController.class.getSimpleName()).start();
            }
        } catch (Exception e) {
            PopupWindow.display("Server connection failed!\n      Wrong Port Number!");
        }
    }

    @FXML
    void enteredMouseAction(MouseEvent event) {
        textFieldPort.setTooltip(ToolTipWindow.createToolTip("Gib bitte mind. 4 Ziffern ein!"));
    }

    @FXML
    void keyReleasedProperty(KeyEvent event) {
        String portString = textFieldPort.getText();
        boolean isDisabled = (portString.isEmpty() || portString.trim().isEmpty() || portString.matches("[a-zA-Z]+") || portString.length() < 4);
        //Bereits im SceneBuilder Disabled.
        btnConnect.setDisable(isDisabled);
    }

    @FXML
    private MenuItem menuQuit;
    @FXML
    void quitAction(ActionEvent event) {
        PopupWindow.display("Disconnected!");
        System.exit(0);
    }
}