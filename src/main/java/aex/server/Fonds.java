package aex.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Random;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Fonds")
public class Fonds {

    @XmlElement(name = "naam")
    private String naam;
    @XmlElement(name = "koers")
    private double koers;

    public Fonds(){

    }

    public Fonds(String naam, double koers){
        this.naam = naam;
        this.koers = koers;
    }

    public String getNaam() {
        return this.naam;
    }

    public double getKoers() {
        return this.koers;
    }

    void updateKoers(){
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
