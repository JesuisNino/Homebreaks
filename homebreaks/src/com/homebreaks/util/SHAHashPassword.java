package com.homebreaks.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Hash passwords using SHA-512.
 *
 * Credit to the following guides for the code below:
 *
 * https://www.baeldung.com/java-password-hashing
 * https://www.javaguides.net/2020/02/java-sha-256-hash-with-salt-example.html
 *
 * Last accessed 02.12.2021
 *
 * @version 1.0
 *
 * @author team055
 *
 */

public class SHAHashPassword {
    private static String hashedPassword;

    /**
     * Hash password string with SHA-512
     *
     * @param password user-input password
     * @param salt array of random bytes to salt the hash
     * @return password hash
     */
    public static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedPasswordBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a String
            StringBuilder sb = new StringBuilder();
            for (byte hashedPasswordByte : hashedPasswordBytes) {
                sb.append(Integer.toString((hashedPasswordByte & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = sb.toString();
        }
        catch(NoSuchAlgorithmException e) {
            System.out.println("[SHAHashPassword] Password hashing failed.");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return hashedPassword;
    }

    /**
     * Generate random salt for hashing password
     *
     * @return array of random bytes
     */
    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
