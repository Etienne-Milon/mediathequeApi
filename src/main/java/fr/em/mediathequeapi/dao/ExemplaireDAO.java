package fr.em.mediathequeapi.dao;

import fr.em.mediathequeapi.metier.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExemplaireDAO extends DAO<Article, Object> {

    protected ExemplaireDAO(Connection connexion) {
        super(connexion);
    }

    public ArrayList<Exemplaire> getExemplaire(Article article) {
        ResultSet rs;
        ArrayList<Exemplaire> liste = new ArrayList<>();
        String Statement = "SELECT EAN13,num_exemplaire,M.id_mediatheque,M.nom,E.id,E.libelle_etat FROM EXEMPLAIRE\n" +
                "                                join mediatheque as M on M.id_mediatheque = EXEMPLAIRE.id_mediatheque\n" +
                "                                join etat as E on EXEMPLAIRE.id_etat = E.id\n" +
                "                WHERE EAN13 = ?";
        try (PreparedStatement pStmt = this.connexion.prepareStatement(Statement)) {
            pStmt.setLong(1, article.getEAN13());
            pStmt.execute();
            rs = pStmt.getResultSet();
            while (rs.next()) {
                Exemplaire newExemplaire = new Exemplaire();
                newExemplaire.setEAN13(rs.getLong(1));
                newExemplaire.setNumExemplaire(rs.getInt(2));
                newExemplaire.setMediatheque(new Mediatheque());
                newExemplaire.getMediatheque().setId(rs.getInt(3));
                newExemplaire.getMediatheque().setNom(rs.getString(4));
                newExemplaire.setEtat(new Etat());
                newExemplaire.getEtat().setId(rs.getInt(5));
                newExemplaire.getEtat().setNom(rs.getString(6));
                liste.add(newExemplaire);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public ArrayList<Exemplaire> getExemplaireById(long ean13){
        ResultSet rs;
        ArrayList<Exemplaire> liste = new ArrayList<>();
        String Statement = "SELECT EAN13,num_exemplaire,M.id_mediatheque,M.nom,E.id,E.libelle_etat FROM EXEMPLAIRE\n" +
                "                                join mediatheque as M on M.id_mediatheque = EXEMPLAIRE.id_mediatheque\n" +
                "                                join etat as E on EXEMPLAIRE.id_etat = E.id\n" +
                "                WHERE EAN13 = ?";
        try (PreparedStatement pStmt = this.connexion.prepareStatement(Statement)) {
            pStmt.setLong(1, ean13);
            pStmt.execute();
            rs = pStmt.getResultSet();
            while (rs.next()) {
                Exemplaire newExemplaire = new Exemplaire();
                newExemplaire.setEAN13(rs.getLong(1));
                newExemplaire.setNumExemplaire(rs.getInt(2));
                newExemplaire.setMediatheque(new Mediatheque());
                newExemplaire.getMediatheque().setId(rs.getInt(3));
                newExemplaire.getMediatheque().setNom(rs.getString(4));
                newExemplaire.setEtat(new Etat());
                newExemplaire.getEtat().setId(rs.getInt(5));
                newExemplaire.getEtat().setNom(rs.getString(6));
                liste.add(newExemplaire);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    
    public Article getById(long ean13) {
        return null;
    }

    
    public ArrayList getAll() {
        return new ArrayList<>();
    }

    
    public ArrayList getLike(Object objet) {
        return new ArrayList<>();
    }


    
    public boolean insert(Article article) throws SQLException {
        ArrayList<Exemplaire> listExemplaire = article.getExemplaires();
        String query = "INSERT INTO EXEMPLAIRE (EAN13, num_exemplaire, id_mediatheque, id_etat) VALUES (?,?,?,?)";
        try (PreparedStatement stmt = connexion.prepareStatement(query)){
            connexion.setAutoCommit(false);
            for (Exemplaire exemplaire : listExemplaire) {
                stmt.setLong(1, article.getEAN13());
                stmt.setInt(2, exemplaire.getNumExemplaire());
                stmt.setInt(3, exemplaire.getMediatheque().getId());
                stmt.setInt(4, exemplaire.getEtat().getId());
                stmt.executeUpdate();
            }
            connexion.commit();
            return true;
        } catch (SQLException ex) {
            connexion.rollback();
            return false;
        }
    }


    
    public boolean update(Article article) {
        return false;
    }

    
    public boolean delete(Article article) {
        return false;
    }
}