package fr.em.mediathequeapi.service;

import fr.em.mediathequeapi.dao.DaoFactory;
import fr.em.mediathequeapi.metier.*;

import java.util.ArrayList;

public class ServiceArticle {

    private ArrayList<Reference> genreFiltre;
    private ArrayList<Reference> mediathequeFiltre;
    private ArrayList<Reference> formatFiltre;
    private ArrayList<Reference> etatFiltre;
    private ArrayList<Langue> langueFiltre;
    private ArrayList<Editeur> editeurFiltre;
    private ArrayList<Personne> personneFiltre;


    public ServiceArticle() {
        genreFiltre = DaoFactory.getGenreDAO().getAll();
        mediathequeFiltre = DaoFactory.getMediathequeDAO().getAll();
        formatFiltre = DaoFactory.getFormatDAO().getAll();
        langueFiltre = DaoFactory.getLangueDAO().getAll();
        editeurFiltre = DaoFactory.getEditeurDAO().getAll();
        etatFiltre = DaoFactory.getEtatDAO().getAll();
        personneFiltre = DaoFactory.getPersonneDAO().getAll();
    }

    public ArrayList<Reference> getGenreFiltre() {
        return genreFiltre;
    }

    public ArrayList<Reference> getFormatFiltre() {
        return formatFiltre;
    }

    public ArrayList<Reference> getMediathequeFiltre() {
        return mediathequeFiltre;
    }

    public ArrayList<Langue> getLangueFiltre() { // Getter utile si implementation d'une recherche d'article par langue
        return langueFiltre;
    }

    public ArrayList<Editeur> getEditeurFiltre() {
        return editeurFiltre;
    }

    public ArrayList<Article> getFilteredArticle(ArticleSearch articleSearch) {
        return DaoFactory.getArticleDAO().getLike(articleSearch);
    }

    public boolean deleteArticle(Article article) {
        return DaoFactory.getArticleDAO().delete(article);
    }

    public ArrayList<Reference> getEtatFiltre() {
        return etatFiltre;
    }

    public ArrayList<Personne> getPersonneFiltre() {
        return personneFiltre;
    }
}
