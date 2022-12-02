package fr.em.mediathequeapi.dao;

import fr.em.mediathequeapi.metier.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class FormatDAO extends DAO<Reference, Object> {
    protected FormatDAO(Connection connexion) {
        super(connexion);
    }

    public Reference getById(long ean13) {
        String query = "SELECT format.id_format, format.libelle FROM ARTICLE JOIN format ON ARTICLE.id_format = format.id_format WHERE ARTICLE.EAN13 = ?";
        ResultSet rs;
        Format format = new Format();
        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setLong(1, ean13);
            stmt.execute();
            rs = stmt.getResultSet();
            while (rs.next()) {
                format.setId(rs.getInt(1));
                format.setNom(rs.getString(2));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }

    
    public ArrayList<Reference> getAll() {
        ArrayList<Reference> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {
            String sqlQuery = "SELECT * FROM FORMAT";
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                Format format = new Format();
                format.setId(rs.getInt(1));
                format.setNom(rs.getString(2));
                liste.add(format);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }


    public boolean insert(Reference format) {
        String sql = "insert into Format (libelle) values (?)";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, format.getNom());
            stmt.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<Reference> getLike(Object objet) {
        return new ArrayList<>();
    }

    
    public boolean update(Reference object) {
        return false;
    }

    
    public boolean delete(Reference object) {
        return false;
    }

    public Format getByArticle(Article article) {
        String query = "SELECT format.id_format, format.libelle FROM ARTICLE JOIN format ON ARTICLE.id_format = format.id_format WHERE ARTICLE.EAN13 = ?";
        ResultSet rs;
        Format format = new Format();
        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setLong(1, article.getEAN13());
            stmt.execute();
            rs = stmt.getResultSet();
            while (rs.next()) {
                format.setId(rs.getInt(1));
                format.setNom(rs.getString(2));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }
}
