package com.homebreaks.main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent Properties.
 *
 * @version 1.0
 *
 * @author team055
 *
 */

public class Property {
    private String email;
    private int propertyID;
    private String name;
    private String description;
    private String location;
    private Address address;
    private List<ChargeBand> chargeBands = new ArrayList<>();
    private int hasBreakfast;
    private Sleeping sleeping;
    private Bathing bathing;
    private Kitchen kitchen;
    private Living living;
    private Utility utility;
    private Outdoors outdoors;

    public Property () { }

    public Property (int propertyID, String name, String location, String description) {
        this.propertyID = propertyID;
        this.name = name;
        this.location = location;
        this.description = description;
    }

    public Property
            (int propertyID, String email, String name, String description, String location, int hasBreakfast, Address address,
             Sleeping sleeping, Bathing bathing, Kitchen kitchen, Living living, Utility utility, Outdoors outdoors) {
        this.propertyID = propertyID;
        this.email = email;
        this.name = name;
        this.description = description;
        this.location = location;
        this.hasBreakfast = hasBreakfast;
        this.address = address;
        this.sleeping = sleeping;
        this.bathing = bathing;
        this.kitchen = kitchen;
        this.living = living;
        this.utility = utility;
        this.outdoors = outdoors;
    }

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public void setName(String name) {this.name = name;}
    public String getName() {return this.name;}
    public void setDescription(String description) {this.description = description;}
    public String getDescription() {return this.description;}
    public void setLocation(String location) {this.location = location;}
    public String getLocation() {return this.location;}
    public void setHasBreakfast(int hasBreakfast) {this.hasBreakfast = hasBreakfast;}
    public int getHasBreakfast() {return hasBreakfast;}
    public Address getAddress() {return this.address;}
    public void setAddress(Address address) {this.address = address;}
    public Sleeping getSleeping() {return this.sleeping;}
    public void setSleeping(Sleeping sleeping) {this.sleeping = sleeping;}
    public Bathing getBathing() { return bathing; }
    public void setBathing(Bathing bathing) { this.bathing = bathing; }
    public Kitchen getKitchen() { return kitchen; }
    public void setKitchen(Kitchen kitchen) { this.kitchen = kitchen; }
    public Living getLiving() { return living; }
    public void setLiving(Living living) { this.living = living; }
    public Utility getUtility() { return utility; }
    public void setUtility(Utility utility) { this.utility = utility; }
    public Outdoors getOutdoors() { return outdoors; }
    public void setOutdoors(Outdoors outdoors) { this.outdoors = outdoors; }
    public List<ChargeBand> getChargeBands() { return chargeBands; }
    public void setChargeBands(List<ChargeBand> chargeBands) { this.chargeBands = chargeBands; }
    public void setPropertyID(int propertyID) {this.propertyID = propertyID;}
    public int getPropertyID() {return this.propertyID;}
    public void addChargeBand(ChargeBand chargeband) {chargeBands.add(chargeband);}

    /**
     * Gets the name of the user from the propertyID
     *
     * @param propertyID ID of the property
     * @return Name of the person retrieved from database
     */
    public String getNameFromID(int propertyID){
        String baseSearchQuery = "SELECT name FROM properties WHERE propertyID = ?";
        ResultSet searchResultSet;
        String returnVal = "";

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
             PreparedStatement preparedSearch = con.prepareStatement(baseSearchQuery)) {
             preparedSearch.setInt(1, propertyID);
             searchResultSet = preparedSearch.executeQuery();
             searchResultSet.next();
             returnVal = searchResultSet.getString("name");
        } catch (SQLException e) {
            throw new IllegalStateException("Connection Failed!", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnVal;
    }

    /**
     * Gets the list of propertyIDs associated with one user's email address
     *
     * @param email email of user
     * @return list of propertyIDs
     */
    public List<Integer> getIDsFromEmail(String email){
        String baseSearchQuery = "SELECT propertyID FROM properties WHERE email = ?";
        ResultSet searchResultSet;
        List<Integer> propertyIDList = new ArrayList<Integer>();
        int returnVal = 0;

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
             PreparedStatement preparedSearch = con.prepareStatement(baseSearchQuery)) {
            preparedSearch.setString(1, email);
            searchResultSet = preparedSearch.executeQuery();
            while(searchResultSet.next()) {
                propertyIDList.add(searchResultSet.getInt("propertyID"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Connection Failed!", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return propertyIDList;
    }

    @Override
    public String toString() {
        return "Property{" +
                "email='" + email + '\'' +
                ", propertyID=" + propertyID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", address=" + address +
                ", chargeBands=" + chargeBands +
                ", hasBreakfast=" + hasBreakfast +
                ", sleeping=" + sleeping +
                ", bathing=" + bathing +
                ", kitchen=" + kitchen +
                ", living=" + living +
                ", utility=" + utility +
                ", outdoors=" + outdoors +
                '}';
    }

}
