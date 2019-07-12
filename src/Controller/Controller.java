package Controller;

import Model.Model;

import java.util.List;
import java.util.Observable;

public class Controller extends Observable{
    private Model model;
    private float[] extendedValues = {0,0,0,0,0,0,0,0,0};
    private boolean maxAndMin = false;

    /**** CONTROLLER ****/
    public Controller(Model model) {
        this.model = model;
    }

    /**** METHODS ****/
    public void finish() {
        model.finish();
    }

    public void update(ActionType type, String param) {
        switch (type) {
            case CONNECT:
                model.setRandom(false);
                model.connectToPort(param);
                break;
            case RANDOM:
                model.setRandom(true);
                break;
            case MODE:
                model.write(Integer.parseInt(param));
                break;
            case FREEZE:
                model.write(Integer.parseInt(param));
                break;
        }
    }

    public float[] getExtendedValues() {
        float[] serial = model.getSerialValues();

        for(int i=0; i<serial.length; i++) {
            extendedValues[i] = serial[i];

            //Set first min and max values
            if(maxAndMin == false) {
                extendedValues[i+3] = serial[i]; //Min
                extendedValues[i+6] = serial[i]; //Max
            }
            else {
                if (serial[i] < extendedValues[i+3])
                    extendedValues[i+3] = serial[i];
                if (serial[i] > extendedValues[i+6])
                    extendedValues[i+6] = serial[i];
            }
        }
        maxAndMin = true;
        return extendedValues;
    }

    public List<String> getPortsNames() {
        List<String> portNames = model.getPortsNames();
        portNames.add("RandomData");
        return portNames;
    }

    public boolean isConnected() {
        return model.isConnected();
    }
    public boolean isRandom() {
        return model.isRandom;
    }
}
