package View;

import Controller.Controller;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

public class CustomGraph extends Parent {

    private Controller controller;

    private LineChart<Number,Number> tempChart;
    private XYChart.Series interieur;
    private XYChart.Series exterieur;

    private LineChart<Number,Number> humiditeChart;
    private XYChart.Series humidite;

    public CustomGraph(Controller controller) {
        this.controller = controller;

        NumberAxis xAxisTemp = new NumberAxis();
        NumberAxis xAxisHumidi = new NumberAxis();
        NumberAxis yAxisHumidi = new NumberAxis();
        NumberAxis yAxisTemp = new NumberAxis();
        yAxisHumidi.setLabel("Humidité (%)");
        yAxisTemp.setLabel("Température (°C)");
        xAxisTemp.setLabel("Temps (s)");
        xAxisHumidi.setLabel("Temps (s)");

        /*Temp*/
        tempChart = new LineChart<Number, Number>(xAxisTemp,yAxisTemp);
        tempChart.setTitle("Température");

        interieur = new XYChart.Series();
        interieur.setName("Extérieur");

        exterieur = new XYChart.Series();
        exterieur.setName("Intérieur");
        /**/
        /*Humidi*/
        humiditeChart = new LineChart<Number, Number>(xAxisHumidi,yAxisHumidi);
        humiditeChart.setTitle("Humidité");

        humidite = new XYChart.Series();
        humidite.setName("Humidité");
        /**/
        tempChart.getData().addAll(interieur,exterieur);
        humiditeChart.getData().add(humidite);

        VBox charts = new VBox();
        charts.getChildren().addAll(tempChart,humiditeChart);

        this.getChildren().add(charts);
    }

    public void refreshCharts(float[] values, double time) {
        interieur.getData().add(new XYChart.Data(time/1000,values[0]));
        exterieur.getData().add(new XYChart.Data(time/1000,values[1]));
        humidite.getData().add(new XYChart.Data(time/1000,values[2]));
    }
}
