package aex.server;

import aex.common.IFonds;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

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
        while (running) {
            try {
                String inString = input.readLine();
                parseCommand(inString);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
        try {
            output.write("#CLS#");
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            System.out.println("Error closing: " + e);
        }
    }

    private void parseCommand(String inString) {
        String command = inString.substring(0, 5);
        switch (command) {
            //Close
            case "#CLS#":
                running = false;
                break;
                //List fondsen
            case "#LST#":
                break;
            //GET
            case "#GET#":
                String tmpStr = inString.substring(6, inString.length() - 1);
                if (tmpStr.length() == 0){
                    //return all
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
        String[] tmp = new String[MockEffectenBeurs.getFondsen().size()-1];
        for(int i=0; i<MockEffectenBeurs.getFondsen().size()-1; i++){
            tmp[i] = MockEffectenBeurs.getFondsen().get(i).getNaam();
        }
        sendFondsInfo(tmp);
    }

    private void sendFondsInfo(String[] fondsIds){
        List<IFonds> fondsenList = MockEffectenBeurs.getFondsen();
        for(String s : fondsIds){
            for (IFonds fonds : fondsenList){
                output.write("#FND#" + fonds.getNaam() + ":" + fonds.getKoers() );
            }
        }
    }

    public void disconnect() {
        running = false;
    }
}
