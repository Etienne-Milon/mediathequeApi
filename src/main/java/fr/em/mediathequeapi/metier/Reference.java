package fr.em.mediathequeapi.metier;

public class Reference {
    private int id;
    private String nom;

    public Reference(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Reference() {
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

    @Override
    public String toString() {
        return nom + " id = " + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reference reference = (Reference) o;
        return id == reference.id && nom.equals(reference.nom);
    }


}
