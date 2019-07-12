package View;

import Controller.Controller;
import Model.Model;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class View extends Application {

    private int graphTime = 0;
    private Controller controller;

    private ControlPanel menu;
    private CustomGraph graph;

    private Timer timer;

    /**** METHODS ****/
    /** Open and close the app **/
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 1010, 810, Color.WHITE);

        Model model = new Model();
        Controller controller = new Controller(model);
        this.controller = controller;

        menu = new ControlPanel(controller);
        graph = new CustomGraph(controller);

        Separator separator = new Separator();
        separator.setValignment(VPos.CENTER);
        separator.setOrientation(Orientation.VERTICAL);
        separator.setPadding(new Insets(10,5,10,5));

        HBox render = new HBox();
        render.getChildren().addAll(graph,separator,menu);

        root.getChildren().add(render);

        primaryStage.setScene(scene);
        primaryStage.show();

        loop();
    }

    @Override
    public void stop(){
        controller.finish();
        timer.cancel();
    }

    /****/
    /** loop for refresh **/
    private void loop() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(controller.isConnected() || controller.isRandom()) {
                        menu.refreshLabel(controller.getExtendedValues());
                        graph.refreshCharts(controller.getExtendedValues(),graphTime);
                    }
                    graphTime += 200;
                });
            }
        }, 0 ,1000);
    }
}
