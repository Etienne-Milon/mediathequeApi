package fr.em.mediathequeapi.metier;

import java.util.ArrayList;
public class Film {

    private Reference realisateur;
    private ArrayList<Reference> acteurs;


    public Film() {
        //Default (no-argument) constructor
    }

    public void setRealisateur(Reference realisateur) {
        this.realisateur = realisateur;
    }

    public void setActeurs(ArrayList<Reference> acteurs) {
        this.acteurs = acteurs;
    }

    public Reference getRealisateur() {
        return realisateur;
    }

    public ArrayList<Reference> getActeurs() {
        return acteurs;
    }

}
