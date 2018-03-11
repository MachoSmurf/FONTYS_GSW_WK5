package aex.server;

import aex.common.IEffectenBeurs;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketListner implements Runnable{

    private final int port;
    private final IEffectenBeurs effectenBeurs;
    public boolean serverrunning = true;
    private List<Thread> threadPool;
    private List<ClientHandler> handlers;

    public SocketListner(int port, IEffectenBeurs effectenBeurs){
        this.effectenBeurs = effectenBeurs;
        this.port = port;
        threadPool = new ArrayList<>();

        threadPool = new ArrayList<>();
        handlers = new ArrayList<>();
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
        try{
            startListening();
        }
        catch (IOException iox){
            System.out.println("Error starting server: " + iox);
        }
    }

    public void stopListening(){
        serverrunning = false;
        for(ClientHandler ch : handlers){
            ch.disconnect();
        }
    }

    private void startListening() throws IOException{
        ServerSocket listener = new ServerSocket(port);
        try{
            while (serverrunning){
                System.out.println("Started listening for clients...");
                Socket s = listener.accept();
                ClientHandler ch = new ClientHandler(s, effectenBeurs);
                handlers.add(ch);
                Thread clientThread = new Thread(ch);
                clientThread.start();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        finally {
            listener.close();
        }
    }
}
