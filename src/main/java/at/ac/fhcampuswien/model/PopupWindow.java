package at.ac.fhcampuswien.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PopupWindow {
    public static void display(String message) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Info!");

        Label label = new Label(message);
        Button button = new Button("OK");
        button.setDefaultButton(true);          //binds button to Enter-Key

        //wenn button geklicked wird, wird die das Fenster geschlossen.
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popupStage.close();
            }
        });

        VBox vBox = new VBox(20);           //(20 = Spacing - Abstand der Elemente zueinander)
        vBox.getChildren().addAll(label, button);   //Elemente der Vbox hinzufügen
        vBox.setAlignment(Pos.CENTER);              //Elemente werden zentriert angeordnet

        Scene scene = new Scene(vBox, 300, 150);

        popupStage.setScene(scene);
        popupStage.showAndWait();                   //Wartet bis das Fenster geschlossen wird
    }
}
