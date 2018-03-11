package aex.server;

import aex.common.IFonds;

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
        return this.naam;
    }

    @Override
    public double getKoers() {
        return this.koers;
    }

    @Override
    public void updateKoers(){
        //berekend een nieuwe mock-koers
        //max fluctuatie is 0.5%
        double rangeMin = -0.005;
        double rangeMax = 0.005;

        Random rand = new Random();
        double upDown = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
        //calculates total percentage value
        double multiplier = 1.0 + upDown;
        this.koers = koers * multiplier;
    }
}
