package com.homebreaks.main;

/**
 * Represent Guests.
 *
 * @version 1.0
 *
 * @author team055
 *
 */

public class Guest extends Person {
    private String publicName;
    public Guest (String userID, String title, String forename, String surname,
                 String email, String mobile, String publicName) {
        super();
        this.publicName = publicName;
    }
}
