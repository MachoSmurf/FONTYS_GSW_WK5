package aex.client;

import aex.common.*;
import aex.server.Fonds;

import java.text.DecimalFormat;
import java.util.*;

public class BannerController {

    private aex.client.AEXBanner banner;
    private Timer pollingTimer;
    private Thread connectionThread;
    MockEffectenBeursService beurs = new MockEffectenBeursService();

    private List<aex.client.Fonds> fondsList;

    public BannerController(aex.client.AEXBanner banner) throws Exception {

        this.banner = banner;

        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();
        pollingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateBanner();
            }
        }, 0, 2000);
    }

    private void updateBanner() {
        try {
            MockEffectenBeurs b = beurs.getMockEffectenBeursPort();
            fondsList = b.koersen();
            fondsenToText();
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

    private void fondsenToText() {
        if (fondsList != null) {
            try {
                String bannerText = "";
                DecimalFormat df = new DecimalFormat("##.00");
                for (aex.client.Fonds f : fondsList) {
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
