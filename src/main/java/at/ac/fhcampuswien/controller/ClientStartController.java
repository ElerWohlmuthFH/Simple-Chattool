package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.model.ToolTipWindow;
import at.ac.fhcampuswien.model.PopupWindow;

import at.ac.fhcampuswien.thread.ReceiveMessageThread;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class ClientStartController implements Initializable{
    public static Socket socket;

    //TODO: ******* STARTSCREEN *******
    //TextFields Host & Port
    @FXML
    private TextField textFieldHost;
    @FXML
    private TextField textFieldPort;

    //Connect Button not usable until TextFields are used! (DISABLE)
    @FXML
    private Button btnConnect;
    //Connect Button clicked --> switch to Screen "ClientChatScreen.fxml"
    //+ Überprüfung ob Host und Port stimmen:
    @FXML
    void btnConnectAction(ActionEvent event) throws Exception {
        String hostString = textFieldHost.getText();
        String portString = textFieldPort.getText();

        try {
            socket = new Socket(hostString, Integer.parseInt(portString));
            if (socket.isConnected() && portString.equals(String.valueOf(socket.getPort()))) {
                PopupWindow.display("Connection successful!");

                Parent root = FXMLLoader.load(getClass().getResource("/ClientChatScreen.fxml"));
                Scene scene = new Scene(root);

                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("- Simple Chat Tool - CLIENT -");
                stage.show();
                new ReceiveMessageThread(socket, ServerChatController.class.getSimpleName()).start(); //starts ReceiveMessageThread
            } else {
                PopupWindow.display("Server connection failed!\n Wrong Hostname or Port Number");
            }
        } catch (UnknownHostException e) {
            PopupWindow.display("Server connection failed!\n Cannot find host '" + hostString + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //das erste was passiert, wenn die Szene geladen wird: u.a. ButtonConnect wird Disabled.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnConnect.setDisable(true);
    }
    //Macht den Button interagierbar:
    @FXML
    void keyReleasedProperty(KeyEvent event) {
        String hostString = textFieldHost.getText();
        String portString = textFieldPort.getText();

        //Hier wird entschieden, ob der Connect-Button wieder aufscheint:
        //Wir schauen ob im TextField etwas drinnen ist oder nicht.
        boolean isDisabled = (
                hostString.isEmpty() || hostString.trim().isEmpty() || hostString.length() < 2)
                || (portString.isEmpty() || portString.trim().isEmpty() || portString.length() < 4);
        //Das übergeben wir an den Connect-Button:
        btnConnect.setDisable(isDisabled);
    }
    //Event wenn man mit der Maus über die TextFields fährt: ToolTip.
    @FXML
    void enteredMouseAction(MouseEvent event) {
        //Wir erstellen ein TextField (es handelt sich um beide) = das Event-Objekt, wo die Quelle drinnen steckt, müssen das in ein TexField casten/umandeln.
        TextField textField = (TextField) event.getSource();
        //Mittels PromptText wird zwischen beiden TextFields unterschieden:
        String promptText = textField.getPromptText();
        if (promptText.equals("hostname")) {
            textFieldHost.setTooltip(ToolTipWindow.createToolTip("Hostname mind. 2 Zeichen"));
        } else if (promptText.equals("port")) {
            textFieldPort.setTooltip(ToolTipWindow.createToolTip("Gib bitte mind. 4 Ziffern ein!"));
        }
    }
    @FXML
    private MenuItem menuQuit;
    @FXML
    void quitAction(ActionEvent event) {
        PopupWindow.display("Disconnected!");
        System.exit(0);
    }
}
