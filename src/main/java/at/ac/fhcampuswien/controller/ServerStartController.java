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
    void btnConnectAction(ActionEvent event) throws Exception {
        String portString = textFieldPort.getText();

        try {
            serverSocket = new ServerSocket(Integer.parseInt(textFieldPort.getText()));
            System.out.println("Ready for connection...");
            socket = serverSocket.accept();

            if (portString.equals("9999")) {
                System.out.println("Connection to Client was successful!");
                PopupWindow.display("Connection to Client was successful!");

                Parent root = FXMLLoader.load(getClass().getResource("/ServerChatScreen.fxml"));
                Scene scene = new Scene(root);

                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("- Simple Chat Tool - SERVER -");
                stage.show();

                new ReceiveMessageThreadForServer(socket, ClientChatController.class.getSimpleName()).start();
            } else {
                System.out.println("Connection failed!");
                PopupWindow.display("Connection failed!\n Port Number");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void enteredMouseAction(MouseEvent event) {
        textFieldPort.setTooltip(ToolTipWindow.createToolTip("Port mind. 4 Zeichen"));
    }

    @FXML
    void keyReleasedProperty(KeyEvent event) {
        String portString = textFieldPort.getText();
        boolean isDisabled = (portString.isEmpty() || portString.trim().isEmpty() || portString.length() < 4);
        //Bereits im SceneBuilder Disabled.
        btnConnect.setDisable(isDisabled);
    }

}