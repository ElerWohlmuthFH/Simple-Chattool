package at.ac.fhcampuswien.model;

import javafx.scene.control.Tooltip;

public class ToolTipWindow {
    //Wenn man über TextField mit der Maus fährt, soll eine Info erscheinen:
    public static Tooltip createToolTip(String message) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(message);
        tooltip.setGraphicTextGap(0.0);

        return tooltip;
    }
}
