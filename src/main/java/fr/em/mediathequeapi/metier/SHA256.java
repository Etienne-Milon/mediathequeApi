package fr.em.mediathequeapi.metier;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class SHA256 {
    public String hash;

    public SHA256(String passwordToHash) throws NoSuchAlgorithmException, NoSuchProviderException {
        //salt = getSalt();
        this.hash = get_SHA_256_SecurePassword(passwordToHash);
    }
    static String salt;

    static {
        try {
            salt = getSalt();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getSalt()
        throws NoSuchAlgorithmException, NoSuchProviderException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }

    private static String get_SHA_256_SecurePassword(String passwordToHash){
        String generatedPassword = null;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i< bytes.length; i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100,16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return generatedPassword;
    }

}
