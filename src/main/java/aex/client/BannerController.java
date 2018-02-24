package aex.client;

import aex.server.Fonds;
import aex.server.IEffectenBeurs;
import aex.server.MockEffectenBeurs;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BannerController {

    private AEXBanner banner;
    private IEffectenBeurs effectenbeurs;
    private Timer pollingTimer;
    private List<IFonds> fondsen;
    private Registry registry;

    public BannerController(AEXBanner banner) {

        this.banner = banner;
        //disable this for embedded effectenbeurs
        startRMIClient();
        //enable this for embedded effectenbeurs
        /*try {
            this.effectenbeurs = new MockEffectenBeurs();
        } catch (RemoteException e) {
            System.out.println("RMI Exception");
            e.printStackTrace();
        }*/

        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();
        // TODO
        pollingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateBanner();
            }
        }, 0, 2000);
    }

    private void updateBanner() {
        try {
            this.fondsen = effectenbeurs.getKoersen();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (fondsen != null) {
            try {
                String bannerText = "";
                DecimalFormat df = new DecimalFormat("##.00");
                for (IFonds f : fondsen) {
                    bannerText = bannerText + f.getNaam() + ": " + df.format(f.getKoers()) + " - ";
                }
                banner.setKoersText(bannerText);
            } catch (RemoteException rmiException) {
                System.out.println("RMI Exception: " + rmiException);
                banner.setKoersText("Fout bij het ophalen van koersinformatie");
            }
        } else {
            banner.setKoersText("Geen koersinfo beschikbaar");
        }
    }

    // Stop banner controller
    public void stop() {
        pollingTimer.cancel();
        // Stop simulation timer of effectenbeurs
        // TODO
        banner.stop();
    }

    private void startRMIClient(){
        try{
            this.registry = LocateRegistry.getRegistry("localhost", 1099);

            if (registry != null){
                for (String s : registry.list()){
                    System.out.println(s);
                }
                effectenbeurs = (IEffectenBeurs) registry.lookup("AEXServer");
            }
        }catch (Exception e){
            System.out.println("Failed RMI: " + e);
        }
    }
}
