package aex.client;

import aex.common.*;

import java.text.DecimalFormat;
import java.util.*;

public class BannerController {

    private aex.client.AEXBanner banner;
    private Timer pollingTimer;
    private ConnectionManager connectionManager;
    private Thread connectionThread;

    private Set<IFonds> fondsSet;

    public BannerController(aex.client.AEXBanner banner) throws Exception {

        this.banner = banner;

        fondsSet = new TreeSet<>();

        connectionManager = new ConnectionManager(this);
        try{
            if (connectionManager.connectToServer()){
                connectionThread = new Thread(connectionManager);
                connectionThread.start();
            }
            else{
                throw new Exception("Could not connect to server!");
            }
        }catch (Exception e) {
            System.out.println("Exception stating connection: " + e);
        }

        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();
        pollingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateBanner();
            }
        }, 0, 2000);
        connectionManager.listFondsen();
    }

    private void updateBanner() {
        try {
            connectionManager.fetchSubscribedFunds();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Stop banner controller
    public void stop() {
        pollingTimer.cancel();
        // Stop simulation timer of effectenbeurs
        banner.stop();
    }

    public void updateFonds(IFonds f) {
        if (fondsSet.contains(f)){
            fondsSet.remove(f);
        }
        fondsSet.add(f);
        fondsenToText();
    }

    private void fondsenToText() {
        if (fondsSet != null) {
            try {
                String bannerText = "";
                DecimalFormat df = new DecimalFormat("##.00");
                for (IFonds f : fondsSet) {
                    bannerText = bannerText + f.getNaam() + ": " + df.format(f.getKoers()) + " - ";
                    //System.out.println(bannerText);
                }
                banner.setKoersText(bannerText);
                System.out.println(bannerText);
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        } else {
            banner.setKoersText("Geen koersinfo beschikbaar");
        }
    }

}
