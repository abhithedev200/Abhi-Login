package com.abhiram.abhilogin.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Random;

public class PremiumLoginEncryption {
    public static final int VERIFY_TOKEN_LENGTH = 4;
    public static final String KEY_PAIR_ALGORITHM = "RSA";


    public static byte[] generateVerifyToken(Random random) {

        byte[] token = new byte[VERIFY_TOKEN_LENGTH];
        random.nextBytes(token);
        return token;
    }

    public static KeyPair generateKeyPair() {
        // KeyPair b()
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_PAIR_ALGORITHM);

            keyPairGenerator.initialize(1_024);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
            //Should be existing in every vm
            throw new ExceptionInInitializerError(nosuchalgorithmexception);
        }
    }

    public static String getServerIdHashString(String sessionId, SecretKey sharedSecret, PublicKey publicKey) {
        // found in LoginListener
        try {
            byte[] serverHash = getServerIdHash(sessionId, publicKey, sharedSecret);
            return (new BigInteger(serverHash)).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static byte[] getServerIdHash(String sessionId, PublicKey publicKey, SecretKey sharedSecret)
            throws NoSuchAlgorithmException {
        // byte[] a(String var0, PublicKey var1, SecretKey var2)
        MessageDigest digest = MessageDigest.getInstance("SHA-1");

        // inlined from byte[] a(String var0, byte[]... var1)
        digest.update(sessionId.getBytes(StandardCharsets.ISO_8859_1));
        digest.update(sharedSecret.getEncoded());
        digest.update(publicKey.getEncoded());

        return digest.digest();
    }

    public static SecretKey decryptSharedKey(PrivateKey privateKey, byte[] sharedKey) throws GeneralSecurityException {
        // SecretKey a(PrivateKey var0, byte[] var1)
        return new SecretKeySpec(decrypt(privateKey, sharedKey), "AES");
    }

    public static byte[] decrypt(PrivateKey key, byte[] data) throws GeneralSecurityException {
        // b(Key var0, byte[] var1)
        Cipher cipher = Cipher.getInstance(key.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);
        return decrypt(cipher, data);
    }

    private static byte[] decrypt(Cipher cipher, byte[] data) throws GeneralSecurityException {
        // inlined: byte[] a(int var0, Key var1, byte[] var2), Cipher a(int var0, String var1, Key var2)
        return cipher.doFinal(data);
    }
}
