package com.homebreaks.main;

import java.sql.*;
import java.util.HashMap;

/**
 * Represent reviews of stays at property.
 *
 * @version 1.0
 *
 * @author team055
 *
 */

public class Review {
    private int reviewID;
    private Booking booking;
    private int propertyID;
    private String description;
    private int cleanliness;
    private int communication;
    private int checkin;
    private int accuracy;
    private int location;
    private int value;
    private Person user;
    private String email;

    // Constructor for getting details from booking list
    public Review(Booking booking, Person user) {
        this.booking = booking;
        this.user = user;
    }

    // Constructor for getting details from database
    public Review(int reviewID, String description, int cleanliness, int communication, int checkin, int accuracy,
    int location, int value, int propertyID, String email) {
        this.reviewID = reviewID;
        this.description = description;
        this.cleanliness = cleanliness;
        this.communication = communication;
        this.checkin = checkin;
        this.accuracy = accuracy;
        this.location = location;
        this.value = value;
        this.propertyID = propertyID;
        this.email = email;
    }

    // Getters
    public int getCleanliness() {
        return cleanliness;
    }
    public int getCommunication() {
        return communication;
    }
    public int getCheckin() {
        return checkin;
    }
    public int getAccuracy() {
        return accuracy;
    }
    public int getLocation() {
        return location;
    }
    public int getValue() {
        return value;
    }
    public String getDescription() { return description; }
    public String getEmail() { return email; }

    /**
     * Calculate average review score over all categories
     *
     * @return average score
     */
    public double getAverageScore (){
        return ((double)cleanliness + (double)communication + (double)checkin +
                (double)accuracy + (double)location + (double)value) / 6;
    }

    /**
     * Add review to database
     *
     * @param description user-input review text
     * @param reviewScore hashmap containing category strings and scores
     * @return average score
     */
    public void createReview(String description, HashMap<String, Integer> reviewScore) {
        this.propertyID = booking.getPropertyID();
        this.email = user.getEmail();
        this.description = description;
        this.cleanliness = reviewScore.get("cleanliness");
        this.communication = reviewScore.get("communication");
        this.checkin = reviewScore.get("checkin");
        this.accuracy = reviewScore.get("accuracy");
        this.location = reviewScore.get("location");
        this.value = reviewScore.get("value");

        String query = "INSERT INTO reviews (description, cleanliness, communication, checkin, accuracy," +
                "location, value, propertyID, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection con = DriverManager.getConnection
                ("jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365")) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, description);
            ps.setInt(2, cleanliness);
            ps.setInt(3, communication);
            ps.setInt(4, checkin);
            ps.setInt(5, accuracy);
            ps.setInt(6, location);
            ps.setInt(7, value);
            ps.setInt(8, propertyID);
            ps.setString(9, email);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + reviewID +
                ", propertyID=" + propertyID +
                ", description='" + description + '\'' +
                ", cleanliness=" + cleanliness +
                ", communication=" + communication +
                ", checkin=" + checkin +
                ", accuracy=" + accuracy +
                ", location=" + location +
                ", value=" + value +
                ", email='" + email + '\'' +
                '}';
    }
}
