package fr.em.mediathequeapi.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class DAO<T,S> {

    protected Connection connexion;

    protected DAO(Connection connexion) {
        this.connexion = connexion;
    }

}
