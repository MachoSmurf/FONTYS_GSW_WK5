package aex.server;

import aex.client.IFonds;

import java.util.Random;

public class Fonds implements IFonds {

    private String naam;
    private double koers;

    public Fonds(String naam, double koers){
        this.naam = naam;
        this.koers = koers;
    }

    @Override
    public String getNaam() {
        return null;
    }

    @Override
    public double getKoers() {
        return 0;
    }

    public void updateKoers(){
        double rangeMin = -0.5;
        double rangeMax = 0.5;

        Random rand = new Random();
        double upDown = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
        //calculates total percentage value
        double multiplier = 100 + upDown;
        this.koers = koers * multiplier;
    }
}
