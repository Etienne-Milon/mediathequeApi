package fr.em.mediathequeapi.dao;

import fr.em.mediathequeapi.metier.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FilmDAO extends DAO<Article, Object>{
    protected FilmDAO(Connection connexion) {
        super(connexion);
    }

    
    public Article getById(long ean13){
        return null;
    }



    public Film getById(Article article) {
        Film newFilm = new Film();
        ResultSet rs1;
        String query = "SELECT FILM.id_personne, P.nom from FILM JOIN PERSONNE as P on FILM.id_personne = P.id_personne WHERE FILM.EAN13 = ?";
        try (PreparedStatement pStmt = this.connexion.prepareStatement(query)) {
            pStmt.setLong(1,article.getEAN13());
            pStmt.execute();
            rs1 = pStmt.getResultSet();
            while(rs1.next()){
                Personne realisateur = new Personne();
                realisateur.setId(rs1.getInt(1));
                realisateur.setNom(rs1.getString(2));
                newFilm.setRealisateur(realisateur);
            }
            rs1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        ResultSet rs2;
        ArrayList<Reference> acteurs = new ArrayList<>();
        String StatementAct = "SELECT P.id_personne, P.nom from FILM JOIN Est_acteur as EA on FILM.EAN13 = EA.EAN13 JOIN PERSONNE as P on EA.id_personne = P.id_personne WHERE FILM.EAN13 = ? ";
        try (PreparedStatement pStmt = this.connexion.prepareStatement(StatementAct)) {
            pStmt.setLong(1, article.getEAN13());
            pStmt.execute();
            rs2 = pStmt.getResultSet();
            while (rs2.next()) {
                Personne newPersonne = new Personne();
                newPersonne.setId(rs2.getInt(1));
                newPersonne.setNom(rs2.getString(2));
                acteurs.add(newPersonne);
            }
            newFilm.setActeurs(acteurs);
            rs2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newFilm;
    }

    
    public ArrayList getAll() {
        ResultSet rs;
        ArrayList<Article> liste = new ArrayList<>();
        String Statement = "SELECT * from FILM";
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
        String query = "INSERT INTO FILM (EAN13, id_personne) VALUES (?,?)";
        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setLong(1, article.getEAN13());
            stmt.setInt(2, article.getFilm().getRealisateur().getId());
            stmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertEstActeur(Article article) throws SQLException {
        String query = "INSERT INTO est_acteur (EAN13, id_personne) VALUES (?,?)";
        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            connexion.setAutoCommit(false);
            for (Reference acteur : article.getFilm().getActeurs()) {
                stmt.setLong(1, article.getEAN13());
                stmt.setInt(2, acteur.getId());
                stmt.executeUpdate();
            }
            connexion.commit();
            return true;
        } catch (SQLException ex) {
            connexion.rollback();
            return false;
        }
    }

    
    public boolean update(Article object) {
        return false;
    }

    
    public boolean delete(Article object) {
        return false;
    }

}
