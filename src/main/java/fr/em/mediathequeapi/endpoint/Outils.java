package fr.em.mediathequeapi.endpoint;

import fr.em.mediathequeapi.dao.DaoFactory;
import fr.em.mediathequeapi.metier.Reference;

import java.util.ArrayList;

public class Outils {

    protected static boolean testPresence(Integer newGenre, ArrayList<Reference> genres) {
        boolean res = false;
        for (Reference ref : genres) {
            if (ref.getId() == DaoFactory.getGenreDAO().getByIdGenre(newGenre).getId()) {
                res = true;
                break;
            }
        }
        return res;
    }
}
