package aex.server;

import aex.client.IFonds;
import com.sun.javafx.collections.ImmutableObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MockEffectenBeurs implements IEffectenBeurs {

    private Timer koersenTimer;
    List<Fonds> fondsen;

    public MockEffectenBeurs(){
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
            return new <IFonds>ImmutableObservableList(fondsen);
        }
        catch (Exception e){
            System.out.println("Could not return fondsen due to casting error");
            return null;
        }
    }

    private void updateKoersen(){
        for(Fonds f : fondsen){
            f.updateKoers();
        }
    }
}