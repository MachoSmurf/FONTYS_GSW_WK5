package aex.server;

import aex.common.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MockEffectenBeurs extends UnicastRemoteObject implements IEffectenBeurs {

    private Timer koersenTimer;
    List<IFonds> fondsen;

    public MockEffectenBeurs() throws RemoteException {
        super();
        koersenTimer = new Timer();
        koersenTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateKoersen();
            }
        }, 0, 2000);

        fondsen = new ArrayList<>();

        fondsen.add(new Fonds("Heineken", 50.00));
        fondsen.add(new Fonds("AHOLD", 29.33));
        fondsen.add(new Fonds("Unilever", 91.98));
        fondsen.add(new Fonds("Sheel", 7.81));
    }

    @Override
    public List<IFonds> getKoersen() {
        try{
            return fondsen;
        }
        catch (Exception e){
            System.out.println("Could not return fondsen due to casting error");
            return null;
        }
    }

    private void updateKoersen(){
        for(IFonds f : fondsen){
            try{
                f.updateKoers();
            }catch (RemoteException rmiException){
                System.out.println("Error updating koers: " + rmiException);
            }
        }
    }
}