package fr.em.mediathequeapi.dao;

import java.sql.Connection;

public class DaoFactory {

    private DaoFactory() {
    }

    private static final Connection connexion = CUIBConnect.getInstance();

    public static ArticleDAO getArticleDAO() {
        return new ArticleDAO(connexion);
    }

    public static FilmDAO getFilmDAO() {
        return new FilmDAO(connexion);
    }

    public static LivreDAO getLivreDAO() {
        return new LivreDAO(connexion);
    }

    public static PisteDAO getPisteDAO() {
        return new PisteDAO(connexion);
    }

    public static GenreDAO getGenreDAO() {
        return new GenreDAO(connexion);
    }

    public static ExemplaireDAO getExemplaireDAO() {
        return new ExemplaireDAO(connexion);
    }

    public static MediathequeDAO getMediathequeDAO() {
        return new MediathequeDAO(connexion);
    }

    public static FormatDAO getFormatDAO() {
        return new FormatDAO(connexion);
    }

    public static EditeurDAO getEditeurDAO() {
        return new EditeurDAO(connexion);
    }

    public static LangueDAO getLangueDAO() {
        return new LangueDAO(connexion);
    }

    public static EtatDAO getEtatDAO() {
        return new EtatDAO(connexion);
    }

    public static PersonneDAO getPersonneDAO() {
        return new PersonneDAO(connexion);
    }

    public static CompteDAO getCompteDAO(){return new CompteDAO(connexion);}
}
