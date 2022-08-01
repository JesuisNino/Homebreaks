package com.homebreaks.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * [NOT USED] Hash passwords using MD5 algorithm.
 * Deprecated. Using SHA-512 instead - left in for demo purposes.
 *
 * @version 1.0
 *
 * @author team055
 *
 */

public class MD5HashPassword {
    static String generatedPassword = null;

    /**
     * [NOT USED] Hash password string with MD5
     * Deprecated. Using SHA-512 instead - left in for demo purposes.
     *
     * @param password user-input password
     * @return password hash
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            // Convert to hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
