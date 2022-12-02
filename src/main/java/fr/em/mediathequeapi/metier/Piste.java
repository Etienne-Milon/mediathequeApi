package fr.em.mediathequeapi.metier;

public class Piste{

    private int id;
    private String nom;
    private String duree;


    private Personne interprete;
    private Personne parolier;
    private Personne compositeur;


    public Piste() {
    }

    public Piste(int id, String nom, String duree) {
        this.id = id;
        this.nom = nom;
        this.duree = duree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDuree() {
        return duree;
    }
    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getNomInterprete() {
        return interprete.getNom();
    }
    public String getNomParolier() {
        return parolier.getNom();
    }
    public String getNomCompositeur() {
        return compositeur.getNom();
    }

    public Personne getInterprete() {
        return interprete;
    }

    public void setInterprete(Personne interprete) {
        this.interprete = interprete;
    }

    public Personne getParolier() {
        return parolier;
    }

    public void setParolier(Personne parolier) {
        this.parolier = parolier;
    }

    public Personne getCompositeur() {
        return compositeur;
    }

    public void setCompositeur(Personne compositeur) {
        this.compositeur = compositeur;
    }
}
