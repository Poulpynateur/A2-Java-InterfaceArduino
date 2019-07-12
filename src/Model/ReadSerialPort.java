package Model;

import gnu.io.*;
import java.awt.Color;
import java.io.*;
import java.util.*;

public class ReadSerialPort implements SerialPortEventListener {

    //Timeout of the connection with the port

    public boolean isConnected = false;

    final static int TIMEOUT = 2000;
    final static String SEPARATOR = " ";

    private Enumeration ports = null;
    private HashMap portMap = new HashMap();

    //Contain the opened port
    private CommPortIdentifier selectedPortIdentifier = null;
    private SerialPort serialPort = null;

    //input and output streams for sending and receiving data
    private InputStream input = null;
    private BufferedReader data = null;
    private OutputStream output = null;

    private float[] serialValues = {0,0,0};

    //Searching active ports
    public List<String> searchForPorts() {
        disconnect();

        List<String> portName = new ArrayList<String>();
        ports = CommPortIdentifier.getPortIdentifiers();

        while (ports.hasMoreElements()) {
            CommPortIdentifier curPort = (CommPortIdentifier)ports.nextElement();
            //get only serial ports
            if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                portMap.put(curPort.getName(), curPort);
                portName.add(curPort.getName());
            }
        }
        return portName;
    }

    //Connect to a specific PORT (must be active)
    public void connect(String portName) {
        selectedPortIdentifier = (CommPortIdentifier)portMap.get(portName);
        CommPort commPort = null;

        try {
            commPort = selectedPortIdentifier.open("TigerControlPanel", TIMEOUT);
            serialPort = (SerialPort)commPort;

            isConnected = true;
        }
        catch (Exception e) {
            System.out.println("Fail to connect to port "+portName+" : "+e.toString());
        }
    }

    //Preprare the PORT for the I/O stream
    public boolean initIOStream() {
        try {
            input = serialPort.getInputStream();
            data = new BufferedReader(new InputStreamReader(input));
            output = serialPort.getOutputStream();

            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Listen for the data of the connected PORT
    public void initListener() {
        try {
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        }
        catch (TooManyListenersException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if(isConnected) {
                serialPort.removeEventListener();
                serialPort.close();

                input.close();
                output.close();

                isConnected = false;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    //Then the PORT send data
    @Override
    public void serialEvent(SerialPortEvent evt) {
        if (evt.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            try {
                if(data.ready()) {
                    String inputLine = data.readLine();

                    String[] chunks = inputLine.split(SEPARATOR);

                    for(int i = 0; i<chunks.length; i++) {
                        serialValues[i] = Float.parseFloat(chunks[i]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeData(int data) {
        try {
            output.write(data);
            output.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public float[] getSerialValues() {
        return serialValues;
    }
}
