package aex.server;

import aex.common.*;

import java.util.List;

public class EffectenBeurs implements IEffectenBeurs, Runnable {
    @Override
    public List<IFonds> getKoersen() {
        return null;
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

    }
}
