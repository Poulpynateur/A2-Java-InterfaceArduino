package Model;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Model {
    private ReadSerialPort serialPort;
    public boolean isRandom = false;

    /**** CONSTRUCTOR ****/
    public Model() {
        serialPort = new ReadSerialPort();
    }

    /**** METHODS ****/
    public void connectToPort(String portName) {
        serialPort.disconnect();

        serialPort.connect(portName);
        serialPort.initIOStream();
        serialPort.initListener();
    }

    public List<String> getPortsNames() {
        return serialPort.searchForPorts();
    }

    public void finish() {
        serialPort.disconnect();
    }

    public float[] getSerialValues() {
        if (isRandom) {
            float[] randomData = new float[3];
            for (int i = 0; i<3; i++) {
                randomData[i] = (float)(ThreadLocalRandom.current().nextInt(0, 100 + 1));
            }
            return randomData;
        }
        else
            return serialPort.getSerialValues();
    }
    public void setRandom(boolean rand) {
        this.isRandom = rand;
    }

    public boolean isConnected() {
        return serialPort.isConnected;
    }

    public void write(int data) {
        serialPort.writeData(data);
    }
}
