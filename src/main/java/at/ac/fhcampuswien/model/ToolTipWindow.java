package at.ac.fhcampuswien.model;

import javafx.scene.control.Tooltip;

public class ToolTipWindow {
    //Wenn man über TextField mit der Maus fährt, soll eine Info erscheinen:
    /* KONSTRUKTOR
    public ToolTipWindow(String message) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(message);           //Wird dem Text hinzugefügt
        tooltip.setGraphicTextGap(0.0);     //Punkt festsetzen, wo das angezeicht wird: Wert = Platz zwischen Grafik und Text
    }
     */
    //METHODE
    public static Tooltip createToolTip(String message) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(message);
        tooltip.setGraphicTextGap(0.0);

        return tooltip;
    }
}
