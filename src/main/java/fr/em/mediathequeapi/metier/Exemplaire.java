package fr.em.mediathequeapi.metier;

import java.io.Serializable;

public class Exemplaire implements Serializable {


    public Exemplaire(){}

    public Exemplaire(int numExemplaire, Mediatheque mediatheque, Etat etat) {
        this.numExemplaire = numExemplaire;
        this.mediatheque = mediatheque;
        this.etat = etat;
    }

    private long EAN13;
    private int numExemplaire;
    private Reference mediatheque;
    private Reference etat;

    public Exemplaire(int numExemplaire) {
        this.numExemplaire = numExemplaire;
    }

    public void setNumExemplaire(int numExemplaire) {
        this.numExemplaire = numExemplaire;
    }

    public int getNumExemplaire(){return numExemplaire;}

    public void setMediatheque(Reference mediatheque) {this.mediatheque = mediatheque ;
    }
    public Reference getMediatheque() {return mediatheque;
    }

    public void setEtat(Reference etat){this.etat = etat;
    }
    public Reference getEtat() {return etat;}

    public void setEAN13(long EAN13) {
        this.EAN13 = EAN13;
    }

    public long getEAN13() {return EAN13;
    }
}


