package fr.em.mediathequeapi.dao;

import fr.em.mediathequeapi.metier.*;

import java.sql.*;
import java.util.ArrayList;

public class GenreDAO extends DAO<Reference, Object> {
    protected GenreDAO(Connection connexion) {
        super(connexion);
    }

    public ArrayList<Reference> getById(long ean13) {
        ArrayList<Reference> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {
            String strCmd = "SELECT genre.id_genre,nom_genre FROM genre \n" +
                    "JOIN avoir_genre ON genre.id_genre = avoir_genre.id_genre\n" +
                    "WHERE avoir_genre.EAN13 = " + ean13;
            ResultSet rs = stmt.executeQuery(strCmd);
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getInt(1));
                genre.setNom(rs.getString(2));
                liste.add(genre);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public ArrayList<Reference> getAll() {
        ArrayList<Reference> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {
            String strCmd = "SELECT * FROM GENRE order by nom_genre";
            ResultSet rs = stmt.executeQuery(strCmd);
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getInt(1));
                genre.setNom(rs.getString(2));

                liste.add(genre);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public ArrayList<Reference> getAllRef(Article article) {
        ArrayList<Reference> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {
            String strCmd = "SELECT * FROM GENRE EXCEPT SELECT GENRE.ID_GENRE, GENRE.NOM_GENRE FROM GENRE JOIN AVOIR_GENRE ON GENRE.ID_GENRE = AVOIR_GENRE.ID_GENRE " +
                    "JOIN ARTICLE ON AVOIR_GENRE.EAN13 = ARTICLE.EAN13 WHERE ARTICLE.EAN13 = " + article.getEAN13();
            ResultSet rs = stmt.executeQuery(strCmd);
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getInt(1));
                genre.setNom(rs.getString(2));

                liste.add(genre);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }


    public ArrayList<Reference> getLike(Object objet) {
        return new ArrayList<>();
    }


    public boolean insert(Reference genre) {
        String sql = "insert into Genre (nom_genre) values (?)";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, genre.getNom());
            stmt.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateGenreInArticle(long ean13, Integer oldGenre, Integer newGenre) {
        String sql = "UPDATE avoir_genre SET id_genre = ? WHERE avoir_genre.EAN13 = ? AND avoir_genre.id_genre = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, newGenre);
            pstmt.setLong(2, ean13);
            pstmt.setInt(3, oldGenre);
            pstmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean insertGenresInArticle(long ean13, ArrayList<Reference> genres) throws SQLException {
        String query = "INSERT INTO AVOIR_GENRE (EAN13,id_genre) VALUES (?,?)";
        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            connexion.setAutoCommit(false);
            for (Reference reference : genres) {
                stmt.setLong(1, ean13);
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


    public boolean deleteGenresInArticle(long ean13, ArrayList<Reference> genres) {
        String sql = "DELETE FROM avoir_genre WHERE EAN13= ? AND id_genre = ? ";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            connexion.setAutoCommit(false);
            for (Reference reference : genres) {
                stmt.setLong(1, ean13);
                stmt.setInt(2, reference.getId());
                stmt.executeUpdate();
            }
            connexion.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean delete(Reference genre) {
        String sql = "delete from GENRE WHERE id_genre = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, genre.getId());
            pstmt.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* TABLE GENRE renvoi l'objet "genre" (id,nom) d'un id_genre */
    public Reference getByIdGenre(Integer idGenre) {
        ResultSet rs;
        Reference genre = new Reference();
        String sql = "SELECT * FROM genre WHERE id_genre = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1,idGenre);
            pstmt.execute();
            rs = pstmt.getResultSet();
            while (rs.next()) {
                genre.setId(rs.getInt(1));
                genre.setNom(rs.getString(2));
            }
            rs.close();
            return genre;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public ArrayList<Reference> getGenre(Article article) {
        ResultSet rs;
        ArrayList<Reference> liste = new ArrayList<>();
        String Statement = "SELECT avoir_genre.id_genre,nom_genre from ARTICLE\n" +
                "join avoir_genre on ARTICLE.EAN13 = avoir_genre.EAN13\n" +
                "join genre on avoir_genre.id_genre = genre.id_genre\n" +
                "where article.EAN13 = ?";
        try (PreparedStatement pStmt = this.connexion.prepareStatement(Statement)) {
            pStmt.setLong(1, article.getEAN13());
            pStmt.execute();
            rs = pStmt.getResultSet();
            while (rs.next()) {
                Genre newGenre = new Genre();
                newGenre.setId(rs.getInt(1));
                newGenre.setNom(rs.getString(2));
                liste.add(newGenre);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public boolean insertAvoirGenre(long ean13, ArrayList<Reference> genres) throws SQLException {
        String query = "INSERT INTO AVOIR_GENRE (EAN13,id_genre) VALUES (?,?)";
        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            connexion.setAutoCommit(false);

            for (Reference reference : genres) {
                stmt.setLong(1, ean13);
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
