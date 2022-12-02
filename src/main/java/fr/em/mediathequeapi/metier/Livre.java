package fr.em.mediathequeapi.metier;

import java.util.ArrayList;

public class Livre{

    private Personne dessinateur;
    private ArrayList<Reference> auteurs;

    public Livre() {
    }

    public Personne getDessinateur() {
        return dessinateur;
    }

    public ArrayList<Reference> getAuteurs() {
        return auteurs;
    }

    public void setDessinateur(Personne dessinateur) {
        this.dessinateur = dessinateur;
    }

    public void setAuteurs(ArrayList<Reference> auteurs) {
        this.auteurs = auteurs;
    }

}
