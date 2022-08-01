package com.homebreaks.main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 * Represents periods with rates set for property rental pricing.
 * Multiple chargebands can be associated with each Property.
 *
 * @version 1.0
 *
 * @author team055
 */

public class ChargeBand {
    private int chargebandID;
    private Date startDate;
    private Date endDate;
    private double pricePerNight;
    private double serviceCharge;
    private double cleaningCharge;
    private int propertyID;

    // Constructors
    public ChargeBand(){}

    // Constructor used in AddProperty before propertyID is generated
    public ChargeBand (Date start, Date end, double priceNight, double service, double cleaning) {
        this.startDate = start;
        this.endDate = end;
        this.pricePerNight = priceNight;
        this.serviceCharge = service;
        this.cleaningCharge = cleaning;
    }

    // Full constructor for adding chargeband to DB
    public ChargeBand (int chargebandID, Date start, Date end, double priceNight, double service,
                       double cleaning, int propertyID) {
        this.chargebandID = chargebandID;
        this.startDate = start;
        this.endDate = end;
        this.pricePerNight = priceNight;
        this.serviceCharge = service;
        this.cleaningCharge = cleaning;
        this.propertyID = propertyID;
    }

    // Getters
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public double getPricePerNight() { return pricePerNight; }
    public double getServiceCharge() { return serviceCharge; }
    public double getCleaningCharge() { return cleaningCharge; }
    public int getPropertyID() { return propertyID;}

    // Setters
    public void setPropertyID(int propertyID) { this.propertyID = propertyID; }

    /**
     * Create chargeband in database
     */
    public void createChargeBand() {
        String query = "INSERT INTO chargebands (startDate, endDate, pricePerNight, serviceCharge, cleaningCharge," +
                "propertyID) VALUES (?, ?, ?, ?, ?, ?);";

        try (Connection con = DriverManager.getConnection
                ("jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365")) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            ps.setDouble(3, pricePerNight);
            ps.setDouble(4, serviceCharge);
            ps.setDouble(5, cleaningCharge);
            ps.setInt(6, propertyID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve all chargebands associated with a Property from database
     *
     * @param propertyID identifier for Property
     * @return List of chargebands
     */
    public List<ChargeBand> returnChargebands (int propertyID) {
        String chargebandSearch = "SELECT * from chargebands WHERE propertyID = ?";
        List<ChargeBand> allPropChargebands = new ArrayList<>();

        try (Connection con = DriverManager.getConnection
                ("jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365")) {
            PreparedStatement ps = con.prepareStatement(chargebandSearch);
            ps.setInt(1, propertyID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int chargebandID = rs.getInt("chargebandID");
                Date startDate = rs.getDate("startDate");
                Date endDate = rs.getDate("endDate");
                double pricePerNight = rs.getDouble("pricePerNight");
                double serviceCharge = rs.getDouble("serviceCharge");
                double cleaningCharge  = rs.getDouble("cleaningCharge");

                ChargeBand cBand = new ChargeBand
                        (chargebandID, startDate, endDate, pricePerNight, serviceCharge, cleaningCharge, propertyID);
                allPropChargebands.add(cBand);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allPropChargebands;
    }

    /**
     * Get chargeband associated with booking start date.
     * Only the start date is considered for setting the price for all nights.
     *
     * @param propertyID Identifier for Property.
     * @param startDate The start date of the Booking.
     * @return The correct chargeband associated with the property for the supplied date.
     */
    public ChargeBand getCorrectBand(int propertyID, Date startDate){
        List<ChargeBand> bandList = returnChargebands(propertyID);
        int correctPos = 0;
        int x=0;
        for (ChargeBand band : bandList){
            if(startDate==band.getStartDate() && startDate.before(band.getEndDate())){
                correctPos=x;
            }
            x++;
        }

        return bandList.get(correctPos);
    }

    @Override
    public String toString() {
        return "ChargeBand{" +
                "chargebandID=" + chargebandID +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", pricePerNight=" + pricePerNight +
                ", serviceCharge=" + serviceCharge +
                ", cleaningCharge=" + cleaningCharge +
                ", propertyID=" + propertyID +
                '}';
    }
}
