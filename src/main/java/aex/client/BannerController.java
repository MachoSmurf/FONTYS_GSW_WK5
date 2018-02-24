package aex.client;

import aex.server.IEffectenBeurs;
import aex.server.MockEffectenBeurs;

import java.util.Timer;

public class BannerController {

    private AEXBanner banner;
    private IEffectenBeurs effectenbeurs;
    private Timer pollingTimer;

    public BannerController(AEXBanner banner) {

        this.banner = banner;
        this.effectenbeurs = new MockEffectenBeurs();

        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();
        // TODO
    }

    // Stop banner controller
    public void stop() {
        pollingTimer.cancel();
        // Stop simulation timer of effectenbeurs
        // TODO
    }
}
