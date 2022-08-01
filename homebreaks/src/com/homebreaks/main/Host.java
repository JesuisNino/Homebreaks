package com.homebreaks.main;

import com.homebreaks.gui.Detail;
import java.sql.*;
import java.util.List;

/**
 * Represent Hosts.
 *
 * @version 1.0
 *
 * @author team055
 *
 */

public class Host extends Person {
    private String publicName;
    private Boolean isSuper;

    // Constructors
    public Host() {}

    public Host (String publicName, Boolean isSuperHost) {
        this.publicName = publicName;
        this.isSuper = isSuperHost;
    }

    public Host (String email, String title, String forename, String surname, String mobile, Boolean isHost,
                 Boolean isGuest, String addressLnOne, String postCode, String publicName, Boolean isSuper) {
        super(email, title, forename, surname, mobile, isHost, isGuest, addressLnOne, postCode);
        this.isSuper = isSuper;
        this.publicName = publicName;
    }

    // Setters
    public void setPublicName(String publicName) {this.publicName = publicName;}
    public void setIsSuper(Boolean isSuper) {this.isSuper = isSuper;}

    // Getters
    public String getPublicName() {return this.publicName;}

    /**
     * Calculate average review score over all reviews for all properties.
     * Used for Superhost calculation.
     *
     * @return average review score for host.
     */
    public double calculateAverageReview() {
        String query = "SELECT propertyID FROM properties WHERE email = ?";
        int numReviews = 0;
        double aggregatedScore = 0.0;

        try (Connection con = DriverManager.getConnection
                ("jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365")) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List<Review> reviews = Detail.getReviews(rs.getInt("propertyID"));
                for (Review r: reviews) {
                    aggregatedScore += r.getAverageScore();
                    numReviews++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aggregatedScore / numReviews;
    }
}
