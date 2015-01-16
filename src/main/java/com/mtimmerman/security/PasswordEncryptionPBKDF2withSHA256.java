package com.mtimmerman.security;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Utf8;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by maarten on 16.01.15.
 */
public final class PasswordEncryptionPBKDF2withSHA256 {
    private static String algorithm = "pbkdf2_sha256";

    /**
     * Getter for the used algorithm
     * @return The algorithm
     */
    public static String getAlgorithm() {
        return algorithm;
    }

    private static byte[] generateSalt() throws NoSuchAlgorithmException {

        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // VERY important to use SecureRandom instead of just Random
        SecureRandom random = new SecureRandom();
        // Generate a 12 byte (71 bit) salt as recommended by RSA PKCS5
        byte[] salt = new byte[12];
        for (int i = 0; i < 12; i++) {
            char c = allowedChars.charAt(
                    random.nextInt(
                            allowedChars.length()
                    )
            );

            salt[i] = (byte) c;
        }

        return salt;
    }

    /**
     * Encrypt the raw password with the salt and given iterations
     * @param password The password
     * @param salt The salt
     * @param iterations The number of iterations
     * @return The encrypted password
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     * @throws java.io.UnsupportedEncodingException
     */
    private static byte[] getEncryptedPassword(CharSequence password, byte[] salt, int iterations)
            throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {

        PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator(new SHA256Digest());
        gen.init(Utf8.encode(password), salt, iterations);
        return ((KeyParameter) gen.generateDerivedParameters(256)).getKey();
    }

    /**
     * Create a new password hash. Used for creation of new passwords
     * @param password The password
     * @return The encrypted password
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     * @throws java.io.UnsupportedEncodingException
     */
    public static String makePassword(CharSequence password)
            throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException{

        int iterations = 12000;
        return makePassword(password, generateSalt(), iterations);
    }

    /**
     * Generate a password hash using an existing salt. Used for verifying passwords
     * @param password The password
     * @param salt The salt
     * @param iterations The number of iterations
     * @return The encrypted password
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     * @throws java.io.UnsupportedEncodingException
     */
    public static String makePassword(CharSequence password, byte[] salt, int iterations)
            throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException{

        byte[] hash = getEncryptedPassword(password, salt, iterations);
        byte[] hashBase64 = Base64.encode(hash);
        return String.format("%s$%s$%s$%s", algorithm, Integer.toString(iterations), new String(salt, "ASCII"), new String(hashBase64));
    }
}