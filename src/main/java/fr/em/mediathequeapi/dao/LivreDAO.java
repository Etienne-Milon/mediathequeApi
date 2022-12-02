package fr.em.mediathequeapi.dao;

import fr.em.mediathequeapi.metier.*;

import java.sql.*;
import java.util.ArrayList;

public class LivreDAO extends DAO<Article, Object> {
    public LivreDAO(Connection connexion) {
        super(connexion);
    }

    
    public Article getById(long ean13) {
        return null;
    }


    public Livre getById(Article article) {
        Livre newLivre = new Livre();

        ResultSet rs;
        ArrayList<Reference> auteurs = new ArrayList<>();
        String Statement_act = "SELECT P.id_personne, P.nom,P2.id_personne, P2.nom from LIVRE\n" +
                "                JOIN Est_auteur as EA on LIVRE.EAN13 = EA.EAN13\n" +
                "                left JOIN PERSONNE as P on EA.id_personne = P.id_personne\n" +
                "                left JOIN PERSONNE as P2 on LIVRE.id_personne = P2.id_personne\n" +
                "                WHERE LIVRE.EAN13 =  ?";
        try (PreparedStatement pStmt = this.connexion.prepareStatement(Statement_act)) {
            pStmt.setLong(1, article.getEAN13());
            pStmt.execute();
            rs = pStmt.getResultSet();

            while (rs.next()) {
                Personne newPersonne = new Personne();
                Personne dessinateur = new Personne();
                newPersonne.setId(rs.getInt(1));
                newPersonne.setNom(rs.getString(2));
                auteurs.add(newPersonne);
                if (rs.getString(2) != null) {
                    dessinateur.setId(rs.getInt(3));
                    dessinateur.setNom(rs.getString(4));
                    newLivre.setDessinateur(dessinateur);
                }
            }
            newLivre.setAuteurs(auteurs);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newLivre;
    }

    
    public ArrayList getAll() {

        ResultSet rs;
        ArrayList<Article> liste = new ArrayList<>();
        String Statement = "SELECT * from LIVRE";
        try (PreparedStatement pStmt = this.connexion.prepareStatement(Statement)) {
            pStmt.execute();
            rs = pStmt.getResultSet();
            while (rs.next()) {
                Article newArticle = new Article();
                newArticle.setEAN13(rs.getLong(1));

                liste.add(newArticle);
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

    
    public boolean insert(Article article) {
        String query = "INSERT INTO LIVRE (EAN13, id_personne) VALUES (?,?)";
        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setLong(1, article.getEAN13());
            if (article.getLivre().getDessinateur().getId() == 0)
                stmt.setNull(2, Types.INTEGER);
            else
                stmt.setInt(2, article.getLivre().getDessinateur().getId());
            stmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean update(Article object) {
        return false;
    }

    
    public boolean delete(Article object) {
        return false;
    }


    public boolean insertEstAuteur(Article article) throws SQLException {
        String query = "INSERT INTO est_auteur (EAN13, id_personne) VALUES (?,?)";
        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            connexion.setAutoCommit(false);
            for (Reference auteur : article.getLivre().getAuteurs()) {
                stmt.setLong(1, article.getEAN13());
                if (auteur.getId() == 0)
                    stmt.setNull(2, Types.INTEGER);
                else
                    stmt.setInt(2, auteur.getId());
                stmt.executeUpdate();
            }
            connexion.commit();
            return true;
        } catch (SQLException ex) {
            connexion.rollback();
            return false;
        }
    }
}
