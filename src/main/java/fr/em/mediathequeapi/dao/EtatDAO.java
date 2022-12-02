package fr.em.mediathequeapi.dao;

import fr.em.mediathequeapi.metier.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class EtatDAO extends DAO <Reference,Object>{
    protected EtatDAO(Connection connexion) {
        super(connexion);
    }

    
    public Reference getById(long ean13) {
        return null;
    }

    
    public ArrayList<Reference> getAll() {
        ArrayList<Reference> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {
            String strCmd = "SELECT * FROM ETAT";
            ResultSet rs = stmt.executeQuery(strCmd);
            while (rs.next()) {
                Etat etat = new Etat();
                etat.setId(rs.getInt(1));
                etat.setNom(rs.getString(2));
                liste.add(etat);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    
    public ArrayList getLike(Object objet) {
        return new ArrayList<>();
    }

    
    public boolean insert(Reference etat) {
        return false;
    }

    
    public boolean update(Reference etat) {
        return false;
    }

    
    public boolean delete(Reference etat) {
        return false;
    }

}
