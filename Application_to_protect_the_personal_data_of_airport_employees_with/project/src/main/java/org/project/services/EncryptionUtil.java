package org.project.services;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionUtil {

    private  final String AES = "AES";
    private  final String secret = "UIOdaNFD312532Ds";
    private  final SecretKey secretKey = new SecretKeySpec(secret.getBytes(), AES);
    
    /**
     * Encrypts the given data using AES encryption.
     *
     * @param data      The data to be encrypted
     * @return The encrypted data as a base64-encoded string
     * @throws Exception If encryption fails
     */
    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts the given data using AES decryption.
     *
     * @param encryptedData The encrypted data as a base64-encoded string
     * @return The decrypted data
     * @throws Exception If decryption fails
     */
    public String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    /**
     * Generates a new AES secret key.
     *
     * @return The generated secret key
     * @throws NoSuchAlgorithmException If no provider supports a KeyGeneratorSpi implementation for the specified algorithm
     */
    public  SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(128); // 128 bits is sufficient for AES
        return keyGenerator.generateKey();
    }


}
