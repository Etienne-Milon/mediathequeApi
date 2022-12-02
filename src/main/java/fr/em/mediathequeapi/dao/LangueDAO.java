package fr.em.mediathequeapi.dao;

import fr.em.mediathequeapi.metier.*;

import java.sql.*;
import java.util.ArrayList;

public class LangueDAO extends DAO<Reference, Object> {
    protected LangueDAO(Connection connexion) {
        super(connexion);
    }

    
    public Reference getById(long ean13) {
        return null;
    }

    
    public ArrayList getAll() {
        ArrayList<Reference> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {
            String strCmd = "SELECT * FROM LANGUE";
            ResultSet rs = stmt.executeQuery(strCmd);
            while (rs.next()) {
                Langue langue = new Langue();
                langue.setId(rs.getInt(1));
                langue.setNom(rs.getString(2));
                liste.add(langue);
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

    
    public boolean insert(Reference langue) {
        String sql = "insert into Langue (nom_langue) values (?) ";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, langue.getNom());
            stmt.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean update(Reference reference) {
        return false;
    }

    
    public boolean delete(Reference langue) {
        String sql = "delete from LANGUE WHERE nom_langue = ? ";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, langue.getNom());
            stmt.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<Reference> getLangue(Article article) {
        ResultSet rs;
        ArrayList<Reference> liste = new ArrayList<>();
        String Statement = "SELECT avoir_langue.id_langue,nom_langue from ARTICLE\n" +
                "left join avoir_langue on ARTICLE.EAN13 = avoir_langue.EAN13\n" +
                "left join langue on avoir_langue.id_langue = langue.id_langue\n" +
                "WHERE ARTICLE.EAN13 = ?";
        try (PreparedStatement pStmt = this.connexion.prepareStatement(Statement)) {
            pStmt.setLong(1, article.getEAN13());
            pStmt.execute();
            rs = pStmt.getResultSet();
            while (rs.next()) {
                Langue newLangue = new Langue();
                newLangue.setId(rs.getInt(1));
                newLangue.setNom(rs.getString(2));
                liste.add(newLangue);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public boolean insertAvoirLangue(Article article) throws SQLException {
        String sql = "INSERT INTO AVOIR_LANGUE (EAN13,id_langue) VALUES (?,?)";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            connexion.setAutoCommit(false);
            for (Reference reference : article.getLangues()) {
                stmt.setLong(1, article.getEAN13());
                stmt.setInt(2, reference.getId());
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
