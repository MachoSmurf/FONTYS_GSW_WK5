package aex.server;

import aex.common.IEffectenBeurs;
import aex.common.IFonds;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread implements Runnable {

    private final Socket socket;
    private boolean running = true;
    private BufferedReader input;
    private PrintWriter output;
    private IEffectenBeurs effectenBeurs;

    public ClientHandler(Socket socket, IEffectenBeurs effectenBeurs) {
        this.effectenBeurs = effectenBeurs;
        this.socket = socket;

        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e);
        }

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
        System.out.println("Clienthandler started");
        while (((running) && (socket.isConnected()))) {
            try {
                String inString = input.readLine();
                parseCommand(inString);
            } catch (Exception e) {
                System.out.println("Error: " + e);
                running = false;
            }

            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            if (socket.isConnected()){
                output.write("#CLS#" + System.getProperty("line.separator"));
                output.flush();
                output.close();
                input.close();
            }
        } catch (Exception e) {
            System.out.println("Error closing: " + e);
        }
    }

    private void parseCommand(String inString) {
        String command = inString.substring(0, 5);
        System.out.println("Received command: " + inString);
        switch (command) {
            //Close
            case "#CLS#":
                running = false;
                break;
                //List fondsen
            case "#LST#":
                sendFondsList();
                break;
            //GET
            case "#GET#":
                String tmpStr = inString.substring(5, inString.length());
                if (tmpStr.length() == 0){
                    //return all
                    sendFondsInfo();
                }
                else{
                    if (tmpStr.charAt(tmpStr.length()-1) == ','){
                        tmpStr = tmpStr.substring(0, tmpStr.length()-1);
                    }
                    String[] fondsenToSend = tmpStr.split(",");
                    sendFondsInfo(fondsenToSend);
                }
                break;
        }
    }

    private void sendFondsList(){
        List<IFonds> fondsen = effectenBeurs.getKoersen();
        String[] tmp = new String[effectenBeurs.getKoersen().size()];
        String message = "#IDX#";
        for(int i=0; i<fondsen.size(); i++){
            message = message + effectenBeurs.getKoersen().get(i).getNaam() + ",";
        }
        message = message + System.getProperty("line.separator");
        output.write(message);
        output.flush();
    }

    private void sendFondsInfo(){
        //fetch all fondsen and send them via the overloaded function
        List<IFonds> fondsen = effectenBeurs.getKoersen();
        String[] tmp = new String[effectenBeurs.getKoersen().size()];
        for(int i=0; i<fondsen.size()-1; i++){
            tmp[i] = effectenBeurs.getKoersen().get(i).getNaam();
        }
        sendFondsInfo(tmp);
    }

    private void sendFondsInfo(String[] fondsIds){
        List<IFonds> fondsenList = effectenBeurs.getKoersen();
        for(String s : fondsIds){
            for (IFonds fonds : fondsenList){
                if (s.equals(fonds.getNaam()))
                {
                    String message = "#FND#" + fonds.getNaam() + "," + fonds.getKoers() + System.getProperty("line.separator");
                    System.out.println(message);
                    output.write(message);
                    output.flush();
                }
            }
        }
    }

    public void disconnect() {
        running = false;
    }
}
