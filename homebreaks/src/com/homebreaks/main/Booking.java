package com.homebreaks.main;

import javax.swing.*;
import java.sql.*;

/**
 * Represent property Bookings.
 *
 * @version 1.0
 *
 * @author team055
 *
 */

public class Booking {
    private final int BOOKINGID;
    private Date startDateBooking;
    private Date endDateBooking;
    private Double totalPrice;
    private String bookingStatus;
    private int propertyID;
    private String email;

    public Booking(int bookingID, Date startDateBooking, Date endDateBooking, Double totalPrice,
                   String bookingStatus, int propertyID, String email) {
        this.BOOKINGID = bookingID;
        this.startDateBooking = startDateBooking;
        this.endDateBooking = endDateBooking;
        this.totalPrice = totalPrice;
        this.bookingStatus = bookingStatus;
        this.propertyID = propertyID;
        this.email = email;
    }

    // Getters
    public Date getStartDateBooking() { return startDateBooking; }
    public Date getEndDateBooking() { return endDateBooking; }
    public Double getTotalPrice() { return totalPrice; }
    public String getBookingStatus() { return bookingStatus; }
    public int getPropertyID() { return propertyID; }
    public String getEmail() { return email; }

    // Setters
    public void setPropertyID(int propertyID) { this.propertyID = propertyID; }
    public void setEmail(String email) { this.email = email; }


    /**
     * Add Booking to database
     */
    public void addBooking(){
        String bookingQuery = "INSERT INTO bookings (startDateBooking, endDateBooking," +
                "totalPrice, bookingStatus, propertyId, email) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
             PreparedStatement preparedBooking = con.prepareStatement(bookingQuery)) {
            preparedBooking.setDate(1, startDateBooking);
            preparedBooking.setDate(2, endDateBooking);
            preparedBooking.setDouble(3, totalPrice);
            preparedBooking.setString(4, bookingStatus);
            preparedBooking.setInt(5, propertyID);
            preparedBooking.setString(6, email);
            preparedBooking.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog
                    (null,
                            "Add Booking Error. SQL Exception.",
                            "Error Message",
                            JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Connection Failed!", ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog
                    (null,
                            "Add Booking Error. Exception.",
                            "Error Message",
                            JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Update a Booking entry when a host accepts a Booking request
     */
    public void accept(){
        String acceptQuery = "UPDATE bookings SET bookingStatus = 'Accepted' WHERE bookingID = ?";

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
             PreparedStatement preparedAccept = con.prepareStatement(acceptQuery)) {
             preparedAccept.setInt(1, BOOKINGID);
             preparedAccept.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog
                    (null,
                            "Error accepting booking. Please contact an administrator.",
                            "Error Message",
                            JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Connection Failed!", ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog
                    (null,
                            "Error accepting booking. Please contact an administrator.",
                            "Error Message",
                            JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    }

    /**
     * Update a Booking entry when a host rejects a Booking request
     */
    public void reject(){
        String rejectQuery = "UPDATE bookings SET bookingStatus = 'Rejected' WHERE bookingID = ?";

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
             PreparedStatement preparedReject = con.prepareStatement(rejectQuery)) {
             preparedReject.setInt(1, BOOKINGID);
             preparedReject.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog
                    (null,
                            "Error rejecting booking. Please contact an administrator.",
                            "Error Message",
                            JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Connection Failed!", ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog
                    (null,
                            "Error rejecting booking. Please contact an administrator.",
                            "Error Message",
                            JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingID=" + BOOKINGID +
                ", startDateBooking=" + startDateBooking +
                ", endDateBooking=" + endDateBooking +
                ", totalPrice=" + totalPrice +
                ", bookingStatus='" + bookingStatus + '\'' +
                ", propertyID=" + propertyID +
                ", email='" + email + '\'' +
                '}';
    }
}
