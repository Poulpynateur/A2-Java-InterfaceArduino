package View;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class InfoMenu extends VBox {

    private Label tempExt;
    private Label tempInt;
    private Label humidite;

    private Label tempExtMax;
    private Label tempIntMax;
    private Label tempIntMin;
    private Label tempExtMin;
    private Label humidiMin;
    private Label humidiMax;

    public InfoMenu() {
        this.setPadding(new Insets(10));

        tempExt = createLabel("0.0°C",1,0,"-fx-font-size: 20pt;-fx-font-weight: bold;");
        tempInt = createLabel("0.0°C",3,0,"-fx-font-size: 20pt;-fx-font-weight: bold;");
        humidite = createLabel("0.0%",1,1,"-fx-font-size: 20pt;-fx-font-weight: bold;");

        tempExtMax = createLabel("0.0°C",1,0,"-fx-font-size: 16pt;-fx-font-weight: bold;");
        tempIntMax = createLabel("0.0°C",3,0,"-fx-font-size: 16pt;-fx-font-weight: bold;");
        humidiMax = createLabel("0.0%",1,1,"-fx-font-size: 16pt;-fx-font-weight: bold;");

        tempIntMin = createLabel("0.0°C",1,0,"-fx-font-size: 16pt;-fx-font-weight: bold;");
        tempExtMin = createLabel("0.0°C",3,0,"-fx-font-size: 16pt;-fx-font-weight: bold;");
        humidiMin = createLabel("0.0%",1,1,"-fx-font-size: 16pt;-fx-font-weight: bold;");

        GridPane actualGrid = createGrid(tempInt, tempExt, humidite);
        GridPane maxGrid = createGrid(tempIntMax, tempExtMax, humidiMax);
        GridPane minGrid = createGrid(tempIntMin, tempExtMin, humidiMin);

        this.getChildren().addAll(
                createLabel("Temps réel : ", 0, 0, "-fx-font-size: 18pt;"),
                actualGrid,
                createLabel("Minimum : ", 0, 0, "-fx-font-size: 18pt;"),
                minGrid,
                createLabel("Maximum : ", 0, 0, "-fx-font-size: 18pt;"),
                maxGrid
        );
    }

    /**** METHODS ***/
    /** Create elements **/
    private GridPane createGrid(Label _tempInt, Label _tempExt, Label _humidite) {
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(50);

        grid.getChildren().addAll(
                createLabel("Intérieur : ",0,0,""), _tempInt,createLabel("Extérieur : ",2,0,""), _tempExt,
                createLabel("Humidité : ",0,1,""), _humidite
        );

        return grid;
    }

    private Label createLabel(String text, int x, int y, String style) {
        Label label = new Label(text);
        GridPane.setConstraints(label,x,y);

        if(style != "")
            label.setStyle(style);

        return label;
    }
    /****/
    /** Refresh info labels **/
    public void refreshLabels(float[] values) {
        tempInt.setText(Float.toString(values[0])+"°C");
        tempExt.setText(Float.toString(values[1])+"°C");
        humidite.setText(Float.toString(values[2])+"%");

        tempIntMin.setText(Float.toString(values[3])+"°C");
        tempExtMin.setText(Float.toString(values[4])+"°C");
        humidiMin.setText(Float.toString(values[5])+"%");

        tempIntMax.setText(Float.toString(values[6])+"°C");
        tempExtMax.setText(Float.toString(values[7])+"°C");
        humidiMax.setText(Float.toString(values[8])+"%");
    }
}
