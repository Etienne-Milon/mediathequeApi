package fr.em.mediathequeapi.dao;

import fr.em.mediathequeapi.metier.Compte;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CompteDAO extends DAO<Compte, Compte>{

    protected CompteDAO(Connection connexion) {
        super(connexion);
    }

    public Compte getById(int id) {
        String query = "SELECT * FROM COMPTE WHERE NUM_ADHERENT = ?";
        Compte compte = new Compte();
        ResultSet rs;
        try(PreparedStatement stmt = connexion.prepareStatement(query)){
            stmt.setLong(1, id);
            stmt.execute();
            rs = stmt.getResultSet();
            while (rs.next()){
                compte.setNumAdherent(rs.getInt(1));
                compte.setNom(rs.getString(2));
                compte.setPrenom(rs.getString(3));
                compte.setAdresse(rs.getString(4));
                compte.setCodePostal(rs.getString(5));
                compte.setEmail(rs.getString(6));
                compte.setPassword(rs.getString(7));
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return compte;
    }

    public Boolean checkAccountExistence(int id) {
        String query = "SELECT * FROM COMPTE WHERE NUM_ADHERENT = ?";
        Compte compte = new Compte();
        ResultSet rs;
        try(PreparedStatement stmt = connexion.prepareStatement(query)){
            stmt.setInt(1, id);
            stmt.execute();
            rs = stmt.getResultSet();
            if (rs.next()){
                rs.close();
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public Boolean checkAccountExistenceByMail(String mail) {
        String query = "SELECT * FROM COMPTE WHERE EMAIL = ?";
        Compte compte = new Compte();
        ResultSet rs;
        try(PreparedStatement stmt = connexion.prepareStatement(query)){
            stmt.setString(1, mail);
            stmt.execute();
            rs = stmt.getResultSet();
            if (rs.next()){
                rs.close();
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public Compte getByMail(String string){
        String query = "SELECT * FROM COMPTE WHERE email = ?";
        Compte compte = new Compte();
        ResultSet rs;
        try(PreparedStatement stmt = connexion.prepareStatement(query)){
            stmt.setString(1, string);
            stmt.execute();
            rs = stmt.getResultSet();
            while (rs.next()){
                compte.setNumAdherent(rs.getInt(1));
                compte.setNom(rs.getString(2));
                compte.setPrenom(rs.getString(3));
                compte.setAdresse(rs.getString(4));
                compte.setEmail(rs.getString(5));
                compte.setPassword(rs.getString(6));
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return compte;
    }

    public boolean validatePwd(String num_adherent, String hash){
        String query = "SELECT * FROM COMPTE WHERE NUM_ADHERENT = ? AND passwordhash = ?";
        Compte compte = new Compte();
        ResultSet rs;
        try(PreparedStatement stmt = connexion.prepareStatement(query)){
            stmt.setString(1, num_adherent);
            stmt.setString(2, hash);
            stmt.execute();
            rs = stmt.getResultSet();
            while (rs.next()){
                compte.setNumAdherent(rs.getInt(1));
                compte.setNom(rs.getString(2));
                compte.setPrenom(rs.getString(3));
                compte.setAdresse(rs.getString(4));
                compte.setEmail(rs.getString(5));
                compte.setPassword(rs.getString(6));
            }
            rs.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean insert(Compte compte) throws SQLException {
        String query = "INSERT INTO COMPTE (NOM,PRENOM,ADRESSE,CODEPOSTAL,EMAIL,PASSWORDHASH,DATE_DEBUT_ADHESION,DATE_FIN_ADHESION) VALUES (?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = connexion.prepareStatement(query)){
            stmt.setString(1, compte.getNom());
            stmt.setString(2, compte.getPrenom());
            stmt.setString(3,compte.getAdresse());
            stmt.setString(4, compte.getCodePostal());
            stmt.setString(5,compte.getEmail());
            stmt.setString(6,compte.getPasswordHash());
            stmt.setDate(7, Date.valueOf(LocalDate.now()));
            stmt.setDate(8,Date.valueOf(LocalDate.now().plusYears(1)));
            stmt.execute();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    
    public Compte getById(long ean13) {
        return null;
    }
    
    public ArrayList<Compte> getAll() {
        return null;
    }

    
    public ArrayList<Compte> getLike(Compte objet) {
        return null;
    }

    
    public boolean delete(Compte object) {
        return false;
    }

    
    public boolean update(Compte object) {
        return false;
    }

}
