package com.abhiram.abhilogin.encrypt;

import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;

public class Encrypt {
    private String encryptedPassword;

    public Encrypt(String password,EncryptType encryptType)
    {
        switch (encryptType)
        {
            case MD5:
                encryptedPassword = toMD5(password);
                break;
            case SHA512:
                encryptedPassword = toSHA512(password);
                break;
            case SHA256:
                encryptedPassword = toSHA256(password);
                break;
            case BCRYPT:
                encryptedPassword = toBcrypt(password);
                break;
        }
    }


    private String toMD5(String password)
    {
        try{
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
           ex.printStackTrace();
        }

        return null;
    }


    private String toSHA512(String password)
    {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }


    private String toSHA256(String password)
    {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            ex.printStackTrace();
        }

        return null;
    }


    private String toBcrypt(String password)
    {
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }


    public String getEncryptedPassword()
    {
        return this.encryptedPassword;
    }
}
