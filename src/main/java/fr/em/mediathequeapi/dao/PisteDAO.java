package fr.em.mediathequeapi.dao;

import fr.em.mediathequeapi.metier.*;

import java.sql.*;
import java.util.ArrayList;

public class PisteDAO extends DAO<Piste, Object> {
    public PisteDAO(Connection connexion) {
        super(connexion);
    }

    
    public Piste getById(long ean13) {
        return null;
    }

    public ArrayList<Piste> getByArticle(Article article) {
        ResultSet rs;
        ArrayList<Piste> liste = new ArrayList<>();

        String statement = "SELECT id_piste,nom_piste,duree, P.id_personne, P.nom as nomInterprete, P2.id_personne, P2.nom as nomParolier, P3.id_personne, P3.nom as nomCompositeur  from piste\n" +
                "                join Personne as P on piste.id_personne = P.id_personne\n" +
                "                left join Personne as P2 on piste.id_personne_est_parolier = P2.id_personne\n" +
                "                left join Personne as P3 on piste.id_personne_est_compositeur = P3.id_personne\n" +
                "                where EAN13 = ? ";
        try (PreparedStatement pStmt = this.connexion.prepareStatement(statement)) {
            pStmt.setLong(1, article.getEAN13());
            pStmt.execute();
            rs = pStmt.getResultSet();
            while (rs.next()) {
                Piste piste = new Piste();
                piste.setId(rs.getInt(1));
                piste.setNom(rs.getString(2));
                piste.setDuree(rs.getString(3));
                piste.setInterprete(new Personne());
                piste.getInterprete().setId(rs.getInt(4));
                piste.getInterprete().setNom(rs.getString(5));
                piste.setParolier(new Personne());
                piste.getParolier().setId(rs.getInt(6));
                piste.getParolier().setNom(rs.getString(7));
                piste.setCompositeur(new Personne());
                piste.getCompositeur().setId(rs.getInt(8));
                piste.getCompositeur().setNom(rs.getString(9));
                liste.add(piste);
            }
            article.setListePiste(liste);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return liste;
    }

    
    public ArrayList getAll() {
        return new ArrayList<>();
    }

    
    public ArrayList getLike(Object object) {
        return new ArrayList<>();
    }

    
    public boolean insert(Piste objet) {
        return false;
    }


    public boolean insert(Article article) throws SQLException {
        String query = "INSERT INTO piste(EAN13, id_piste, nom_piste, duree, id_personne, id_personne_est_parolier, id_personne_est_compositeur) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            connexion.setAutoCommit(false);
            for (Piste piste : article.getListePiste()) {
                stmt.setLong(1, article.getEAN13());
                stmt.setInt(2, piste.getId());
                stmt.setString(3, piste.getNom());
                stmt.setString(4, piste.getDuree());
                stmt.setInt(5, piste.getInterprete().getId());

                if (piste.getParolier().getId() == 0)
                    stmt.setNull(6, Types.INTEGER);
                else
                    stmt.setInt(6, piste.getParolier().getId());

                if (piste.getCompositeur().getId() == 0)
                    stmt.setNull(7, Types.INTEGER);
                else
                    stmt.setInt(7, piste.getCompositeur().getId());
                stmt.executeUpdate();
            }
            connexion.commit();
            return true;
        } catch (SQLException ex) {
            connexion.rollback();
            return false;
        }
    }

    
    public boolean update(Piste piste) {
        return false;
    }

    
    public boolean delete(Piste piste) {
        return false;
    }


}
