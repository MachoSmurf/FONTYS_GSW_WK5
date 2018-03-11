package aex.server;

import aex.common.*;
import java.util.Collections;

import java.util.*;

public class MockEffectenBeurs implements IEffectenBeurs, Runnable {

    private Timer koersenTimer;
    private List<IFonds> fondsen;
    private static List<IFonds> fondsenStatic;

    public MockEffectenBeurs(){
        super();

        fondsen = new ArrayList<>();
        fondsenStatic = new ArrayList<>();
        fondsenStatic = fondsen;

        fondsen.add(new Fonds("Heineken", 50.00));
        fondsen.add(new Fonds("AHOLD", 29.33));
        fondsen.add(new Fonds("Unilever", 91.98));
        fondsen.add(new Fonds("Sheel", 7.81));
    }

    public static List<IFonds> getFondsen(){
        return Collections.unmodifiableList(fondsenStatic);
    }

    @Override
    public List<IFonds> getKoersen() {
        try{
            return Collections.unmodifiableList(fondsen);
        }
        catch (Exception e){
            System.out.println("Could not return fondsen due to casting error");
            return null;
        }
    }

    private void updateKoersen(){
        for(IFonds f : fondsen){
            try{
                if(f instanceof Fonds){
                    Fonds fonds = (Fonds)f;
                    fonds.updateKoers();
                    System.out.println(f.getNaam() + ": " + f.getKoers());
                }
            }catch (Exception e){
                System.out.println("Error updating koers: " + e);
            }
        }
        fondsenStatic = fondsen;
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
        koersenTimer = new Timer();
        koersenTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateKoersen();
            }
        }, 0, 2000);
    }
}