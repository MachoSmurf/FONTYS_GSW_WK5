package aex.server;

import aex.common.*;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.Endpoint;
import java.util.Collections;

import java.util.*;

@WebService
public class MockEffectenBeurs{

    private Timer koersenTimer;
    @XmlElement(name = "fondsen")
    public List<Fonds> fondsen;

    public MockEffectenBeurs(){
        super();

        fondsen = new ArrayList<>();

        fondsen.add(new Fonds("Heineken", 50.00));
        fondsen.add(new Fonds("AHOLD", 29.33));
        fondsen.add(new Fonds("Unilever", 91.98));
        fondsen.add(new Fonds("Shell", 7.81));

        koersenTimer = new Timer();
        koersenTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateKoersen();
            }
        }, 0, 2000);
    }

    public List<Fonds> koersen() {
        try{
            return fondsen;
        }
        catch (Exception e){
            System.out.println("Could not return fondsen due to casting error");
            return null;
        }
    }

    private void updateKoersen(){
        for(Fonds f : fondsen){
            try{
                if(f instanceof Fonds){
                    Fonds fonds = f;
                    fonds.updateKoers();
                }
            }catch (Exception e){
                System.out.println("Error updating koers: " + e);
            }
        }
    }

    private void setKoersenTimer(){

    }
}