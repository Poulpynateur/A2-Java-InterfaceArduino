package View;

import Controller.ActionType;
import Controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ControlPanel extends Parent {

    private Controller controller;

    private ComboBox portChoice;
    private Button refreshPort;
    private ComboBox modeType;

    private Label froid;
    private SwitchButton isCold;

    private InfoMenu infoMenu;

    public ControlPanel(Controller controller) {
        this.controller = controller;

        GridPane controlMenu = getControlMenu();

        Separator separator = new Separator();
        separator.setValignment(VPos.CENTER);
        separator.setPadding(new Insets(5,10,5,10));

        infoMenu = new InfoMenu();

        manageEvents();

        VBox menu = new VBox();
        menu.getChildren().addAll(controlMenu,separator,infoMenu);

        this.getChildren().addAll(menu);
    }

    private GridPane getControlMenu() {
        GridPane controlMenu = new GridPane();
        controlMenu.setVgap(10);
        controlMenu.setHgap(10);

        portChoice = new ComboBox();
        portChoice.getItems().addAll(controller.getPortsNames());
        portChoice.setMinWidth(150);

        refreshPort = new Button("Rafraichir");

        modeType = new ComboBox();
        modeType.getItems().addAll("Automatique", "Manuel");
        modeType.getSelectionModel().selectFirst();
        modeType.setMinWidth(150);

        froid = createLabel("Froid",0,2);
        froid.setVisible(false);
        isCold = new SwitchButton();
        isCold.setVisible(false);

        GridPane.setConstraints(refreshPort,2,0);
        GridPane.setConstraints(portChoice,1,0);
        GridPane.setConstraints(modeType,1,1);
        GridPane.setConstraints(isCold,1,2);
        controlMenu.getChildren().addAll(
                createLabel("Nom du port",0,0),portChoice,refreshPort,
                createLabel("Mode de fonctionnement",0,1),modeType,
                froid,isCold);
        controlMenu.setPadding(new Insets(10));

        return controlMenu;
    }

    private Label createLabel(String text, int x, int y) {
        Label label = new Label(text);
        GridPane.setConstraints(label,x,y);
        return label;
    }

    /** Handles buttons/ComboBox events **/
    public void manageEvents() {
        refreshPort.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                portChoice.getItems().clear();
                portChoice.getItems().addAll(controller.getPortsNames());
            }
        });
        portChoice.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String portName = portChoice.getValue().toString();

                if(portName.equals("RandomData"))
                    controller.update(ActionType.RANDOM,"");
                else
                    controller.update(ActionType.CONNECT, portChoice.getValue().toString());
            }
        });
        modeType.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String choice = modeType.getValue().toString();

                if(choice.equals("Manuel")) {
                    showSubMenu(true);
                    controller.update(ActionType.MODE,"2");
                }
                else {
                    showSubMenu(false);
                    controller.update(ActionType.MODE,"1");
                }
            }
        });
        isCold.switchedOnProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(modeType.getValue().toString().equals("Manuel")) {
                    if (newValue == true)
                        controller.update(ActionType.FREEZE,"3");
                    else
                        controller.update(ActionType.FREEZE,"2");
                }
            }
        });
    }
    private void showSubMenu(boolean bool) {
        froid.setVisible(bool);
        isCold.setVisible(bool);
    }

    /** Refresh labels **/
    public void refreshLabel(float[] values) {
        infoMenu.refreshLabels(values);
    }
}