package com.mtimmerman.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by maarten on 16.01.15.
 */
@Component
public class PasswordEncoderImpl implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        try {
            return PasswordEncryptionPBKDF2withSHA256.makePassword(
                    charSequence
            );
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeySpecException ignored) {
        }

        return "";
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        // Split password hash in to algorithm, iterations, salt and hash
        String [] parts = s.split("\\$");

        String algorithm = parts[0];

        if (algorithm.equals(PasswordEncryptionPBKDF2withSHA256.getAlgorithm())) {
            int iterations = Integer.parseInt(parts[1]);
            String salt = parts[2];
            byte[] saltBytes = salt.getBytes();

            try {
                // Recreate the password using the given raw password, the saved salt and iterations
                String encoded = PasswordEncryptionPBKDF2withSHA256.makePassword(charSequence, saltBytes, iterations);

                // User is logged in when these values match
                return encoded.equals(s);

            } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeySpecException ignored) {
                // TODO: Error handling
            }
        }

        return false;
    }
}
