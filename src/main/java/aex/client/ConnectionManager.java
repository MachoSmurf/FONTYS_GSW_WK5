package aex.client;

import aex.common.IFonds;
import aex.server.Fonds;

import javax.sound.midi.SysexMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ConnectionManager implements Runnable {

    BannerController bannerController;
    private final String serverAddress = "127.0.0.1";
    private final int port = 5555;
    private Socket connectionSocket;
    private BufferedReader input;
    private PrintWriter output;
    private boolean maintainConnection = true;

    public ConnectionManager(BannerController bannerController) {
        this.bannerController = bannerController;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        if (connectionSocket.isConnected()) {
            while (maintainConnection) {
                try {
                    String inString = input.readLine();
                    parseCommand(inString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void parseCommand(String inString) {
        String command = inString.substring(0, 5);
        switch (command) {
            //Close
            case "#CLS#":
                maintainConnection = false;
                break;
            case "#FND#":
                String fondsData = inString.substring(5, inString.length());
                try{
                    IFonds f = new Fonds(fondsData.split(",")[0], Double.parseDouble(fondsData.split(",")[1]));
                    bannerController.updateFonds(f);
                }catch (Exception e){
                    System.out.println("Error parsing Fonds data from server: " + e);
                }
                break;
        }
    }

    public void stopConnection() {
        maintainConnection = false;
    }

    public boolean connectToServer() {
        try {
            connectionSocket = new Socket(serverAddress, port);
            input = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            output = new PrintWriter(connectionSocket.getOutputStream());
            return true;
        } catch (Exception e) {
            System.out.println("Error connecting to server: " + e);
            return false;
        }
    }

    private void sendCommand(String message) {
        output.write(message);
        output.flush();
        System.out.println("Send command: " + message);
    }

    public void listFondsen() {
        try {
            String message = "#LST#" + System.getProperty("line.separator");
            sendCommand(message);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void getAllFondsen() {
        try {
            String message = "#GET#" + System.getProperty("line.separator");
            sendCommand(message);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void getFonds(List<String> fondsNamen) {
        try {
            String message = "#GET#";
            for (String s : fondsNamen) {
                message = message + "," + s;
            }
            message = message + System.getProperty("line.separator");
            sendCommand(message);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
