package fr.em.mediathequeapi.dao;

import fr.em.mediathequeapi.metier.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PersonneDAO extends DAO<Personne, Object> {
    protected PersonneDAO(Connection connexion) {
        super(connexion);
    }

    
    public Personne getById(long ean13) {
        return null;
    }

    
    public ArrayList<Personne> getAll() {
        ArrayList<Personne> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {
            String query = "SELECT DISTINCT Personne.id_personne, Personne.nom FROM Personne ORDER BY nom";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Personne personne = new Personne();
                personne.setId(rs.getInt(1));
                personne.setNom(rs.getString(2));
                liste.add(personne);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    
    public ArrayList<Personne> getLike(Object objet) {
        return new ArrayList<>();
    }

    
    public boolean insert(Personne objet) {
        return false;
    }

    
    public boolean update(Personne object) {
        return false;
    }

    
    public boolean delete(Personne object) {
        return false;
    }


}
