package com.homebreaks.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex pattern matching for user input validation
 *
 * @version 1.0 05.12.2022
 *
 * @author team055
 *
 */

public class FieldValidator {

    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@" +
            "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    private static final String PHONE_PATTERN = "^(\\+\\d{1,16})$|^\\d{1,16}$"; //simpler phone pattern
    private static final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

    private static final String NAME_PATTERN = "^[a-zA-Z\\s'-]*$";
    private static final Pattern namePattern = Pattern.compile((NAME_PATTERN));

    private static final String POST_ZIP_PATTERN = "^[a-zA-Z0-9\\s-()]{1,12}$";
    private static final Pattern postZipPattern = Pattern.compile(POST_ZIP_PATTERN);

    private static final String LATIN_AND_SPACE_PATTERN = "^[a-zA-Z\\s-',]*$";
    private static final Pattern latinAndSpacePattern = Pattern.compile(LATIN_AND_SPACE_PATTERN);

    private static final String LATIN_NUM_SPACE_PATTERN = "^[a-zA-Z0-9\\s-'.]*$";
    private static final Pattern latinNumSpacePattern = Pattern.compile(LATIN_NUM_SPACE_PATTERN);

    private static final String ASCII_PATTERN = "^[\\p{ASCII}’]*$";
    private static final Pattern asciiPattern = Pattern.compile(ASCII_PATTERN);

    public static boolean isEmailValid(final String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPhoneValid(final String phone) {
        Matcher matcher = phonePattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean isPasswordValid(final String password) {
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isNameValid(final String name) {
        Matcher matcher = namePattern.matcher(name);
        return matcher.matches();
    }

    public static boolean isPostZipValid(final String postZip) {
        Matcher matcher = postZipPattern.matcher(postZip);
        return matcher.matches();
    }

    public static boolean isLatinAndSpace(final String latinSpace) {
        Matcher matcher = latinAndSpacePattern.matcher(latinSpace);
        return matcher.matches();
    }

    public static boolean isLatinNumSpace(final String latinNumSpace) {
        Matcher matcher = latinNumSpacePattern.matcher(latinNumSpace);
        return matcher.matches();
    }

    public static boolean isASCII(final String ascii) {
        Matcher matcher = asciiPattern.matcher(ascii);
        return matcher.matches();
    }

}
