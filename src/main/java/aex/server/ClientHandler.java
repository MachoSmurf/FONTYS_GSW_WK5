package aex.server;

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

    public ClientHandler(Socket socket) {
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
        while ((running) && (socket.isConnected())) {
            try {
                String inString = input.readLine();
                parseCommand(inString);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }

            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            if (socket.isConnected()){
                output.write("#CLS#");
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
                sendFondsInfo();
                break;
            //GET
            case "#GET#":
                String tmpStr = inString.substring(5, inString.length());
                if (tmpStr.length() == 0){
                    //return all
                    sendFondsInfo();
                }
                else{
                    String[] fondsenToSend = tmpStr.split(",");
                    sendFondsInfo(fondsenToSend);
                }
                break;
        }
    }

    private void sendFondsInfo(){
        //fetch all fondsen and send them via the overloaded function
        List<IFonds> fondsen = MockEffectenBeurs.getFondsen();
        String[] tmp = new String[MockEffectenBeurs.getFondsen().size()];
        for(int i=0; i<fondsen.size()-1; i++){
            tmp[i] = MockEffectenBeurs.getFondsen().get(i).getNaam();
        }
        sendFondsInfo(tmp);
    }

    private void sendFondsInfo(String[] fondsIds){
        List<IFonds> fondsenList = MockEffectenBeurs.getFondsen();
        for(String s : fondsIds){
            for (IFonds fonds : fondsenList){
                if (s == fonds.getNaam())
                {
                    output.write("#FND#" + fonds.getNaam() + ":" + fonds.getKoers() + System.getProperty("line.separator"));
                    output.flush();
                }
            }
        }
    }

    public void disconnect() {
        running = false;
    }
}
