package fr.em.mediathequeapi.dao;

import fr.em.mediathequeapi.metier.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class EditeurDAO extends DAO<Reference,Object>{
    protected EditeurDAO(Connection connexion) {
        super(connexion);
    }

    
    public Reference getById(long ean13) {
        Reference editeur = new Reference();
        try (Statement stmt = connexion.createStatement()) {
            String str = "SELECT ARTICLE.id_editeur, editeur.nom_editeur from ARTICLE\n" +
                    "join editeur on ARTICLE.id_editeur = editeur.id_editeur\n" +
                    "where ARTICLE.EAN13 = " + ean13;
            ResultSet rs = stmt.executeQuery(str);
            while (rs.next()) {
                editeur.setId(rs.getInt(1));
                editeur.setNom(rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editeur;
    }

    public Editeur getByArticle(Article article) {
        String query = "SELECT editeur.id_editeur, editeur.nom_editeur FROM ARTICLE JOIN editeur ON ARTICLE.id_editeur = editeur.id_editeur WHERE ARTICLE.EAN13 = ?";
        Editeur editeur = new Editeur();
        ResultSet rs;
        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setLong(1, article.getEAN13());
            stmt.execute();
            rs = stmt.getResultSet();
            while (rs.next()) {
                editeur.setId(rs.getInt(1));
                editeur.setNom(rs.getString(2));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return editeur;
    }

    
    public ArrayList getAll() {
        ArrayList<Editeur> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {
            String strCmd = "SELECT * FROM EDITEUR";
            ResultSet rs = stmt.executeQuery(strCmd);
            while (rs.next()) {
                Editeur editeur = new Editeur();
                editeur.setId(rs.getInt(1));
                editeur.setNom(rs.getString(2));
                liste.add(editeur);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    
    public boolean insert(Reference editeur) {
        String sql = "insert into Editeur (nom_editeur) values (?)";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, editeur.getNom());
            stmt.execute(sql);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public ArrayList getLike(Object objet) {
        return new ArrayList<>();
    }

    
    public boolean update(Reference object) {
        return false;
    }

    
    public boolean delete(Reference object) {
        return false;
    }


}
