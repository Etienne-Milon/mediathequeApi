package fr.em.mediathequeapi.dao;


import fr.em.mediathequeapi.service.ArticleSearch;
import fr.em.mediathequeapi.metier.*;

import java.sql.*;
import java.util.ArrayList;

public class ArticleDAO extends DAO<Article, ArticleSearch>
{
    public ArticleDAO(Connection connexion)  {
        super(connexion);
    }

    public Article getById(long ean13) {
        String query = "SELECT * FROM ARTICLE WHERE EAN13 = ?";
        ResultSet rs;
        Article articleBis = new Article();
        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setLong(1, ean13);
            stmt.execute();
            rs = stmt.getResultSet();
            while (rs.next()) {
                articleBis.setEAN13(rs.getLong(1));
                articleBis.setTitre(rs.getString(3));
                articleBis.setFormat(new Format(rs.getInt(5),rs.getString(6)));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articleBis;
    }

    public Article getById(Article article) {
        String query = "SELECT * FROM ARTICLE WHERE EAN13 = ?";
        ResultSet rs;
        Article articleBis = new Article();
        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setLong(1, article.getEAN13());
            stmt.execute();
            rs = stmt.getResultSet();
            while (rs.next()) {
                articleBis.setEAN13(rs.getLong(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articleBis;
    }

    public ArrayList<Article> getLike(ArticleSearch articleSearch) {
        ResultSet rs;
        ArrayList<Article> liste = new ArrayList<>();
        String procedureStockee = "{call dbo.sp_QBE_Vue_Article2 (?,?,?,?)}";
        try (CallableStatement cStmt = this.connexion.prepareCall(procedureStockee)) {
            cStmt.setString(1, articleSearch.getString());
            cStmt.setInt(2, articleSearch.getFormat().getId());
            cStmt.setInt(3, articleSearch.getGenre().getId());
            cStmt.setInt(4, articleSearch.getMediatheque().getId());
            cStmt.execute();
            rs = cStmt.getResultSet();
            while (rs.next()) {
                Article newArticle = new Article();
                newArticle.setEAN13(rs.getLong(1));
                newArticle.setTitre(rs.getString(2));
                newArticle.setEditeur(new Editeur());
                newArticle.getEditeur().setNom(rs.getString(3));
                newArticle.setFormat(new Format());
                newArticle.getFormat().setNom(rs.getString(4));
                liste.add(newArticle);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }


    public ArrayList<Article> getAll() {
        ResultSet rs;
        ArrayList<Article> liste = new ArrayList<>();
        String statement = "SELECT EAN13, titre, E.id_editeur, E.nom_editeur, F.id_format, F.libelle FROM ARTICLE as A join editeur as E on E.id_editeur = A.id_editeur join [format] as F on F.id_format = A.id_format";
        try (PreparedStatement pStmt = this.connexion.prepareStatement(statement)) {
            pStmt.execute();
            rs = pStmt.getResultSet();
            while (rs.next()) {
                Article newArticle = new Article();
                newArticle.setEAN13(rs.getLong(1));
                newArticle.setTitre(rs.getString(2));
                newArticle.setEditeur(new Editeur());
                newArticle.getEditeur().setId(rs.getInt(3));
                newArticle.getEditeur().setNom(rs.getString(4));
                newArticle.setFormat(new Format());
                newArticle.getFormat().setId(rs.getInt(5));
                newArticle.getFormat().setNom(rs.getString(6));
                liste.add(newArticle);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }


    public boolean insert(Article article) {
        String query = "INSERT INTO ARTICLE (EAN13, prix_achat, titre, grande_valeur, id_editeur, id_format, id_serie) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setLong(1, article.getEAN13());
            stmt.setBigDecimal(2, article.getPrixAchat());
            stmt.setString(3, article.getTitre());
            stmt.setInt(4, article.getGrandeValeur());
            stmt.setInt(5, article.getEditeur().getId());
            stmt.setInt(6, article.getFormat().getId());
            stmt.setNull(7, Types.INTEGER);

            stmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean update(Article article) {
        return false;
    }


    public boolean delete(Article article) {
        String statement = "DELETE FROM ARTICLE WHERE EAN13 = ?";
        try (PreparedStatement pStmt = this.connexion.prepareStatement(statement)) {
            pStmt.setLong(1, article.getEAN13());
            pStmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
