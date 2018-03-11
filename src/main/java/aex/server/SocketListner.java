package aex.server;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketListner implements Runnable{

    private final int port;
    public boolean serverrunning = true;
    private List<Thread> threadPool;
    private List<ClientHandler> handlers;

    public SocketListner(int port){
        this.port = port;
        threadPool = new ArrayList<>();
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
                ClientHandler ch = new ClientHandler(listener.accept());
                handlers.add(ch);
                Thread clientThread = new Thread(ch);
                clientThread.start();
            }
        }
        finally {
            listener.close();
        }
    }
}
