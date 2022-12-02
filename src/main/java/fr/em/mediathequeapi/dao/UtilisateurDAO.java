package fr.em.mediathequeapi.dao;

import fr.em.mediathequeapi.metier.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtilisateurDAO extends DAO<Utilisateur, Utilisateur> {

    protected UtilisateurDAO(Connection connexion) {
        super(connexion);
    }

    
    public Utilisateur getById(long ean13) {
        return null;
    }

    public ArrayList<Utilisateur> getById(Compte compte){
        String query = "SELECT * FROM UTILISATEUR WHERE NUM_ADHERENT = ?";
        ArrayList<Utilisateur> liste = new ArrayList<>();
        ResultSet rs;
        try(PreparedStatement stmt = connexion.prepareStatement(query)){
            stmt.setInt(1, compte.getNumAdherent());
            stmt.execute();
            rs = stmt.getResultSet();
            while(rs.next()){
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setIdUtilisateur(rs.getInt(1));
                utilisateur.setNom(rs.getString(2));
                utilisateur.setIdCompte(rs.getInt(3));
                liste.add(utilisateur);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }

    
    public ArrayList<Utilisateur> getAll() {
        return null;
    }

    
    public ArrayList<Utilisateur> getLike(Utilisateur objet) {
        return null;
    }

    
    public boolean insert(Utilisateur utilisateur) throws SQLException {
        String query = "INSERT INTO UTILISATEUR(NOM_UTILISATEUR, NUM_ADHERENT) VALUES (?,?)";
        try(PreparedStatement stmt = connexion.prepareStatement(query)){
            stmt.setString(1, utilisateur.getNom());
            stmt.setInt(2, utilisateur.getIdCompte());
            stmt.execute();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean update(Utilisateur object) {
        return false;
    }

    
    public boolean delete(Utilisateur object) {
        return false;
    }
}
