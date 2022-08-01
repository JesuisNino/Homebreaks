package com.homebreaks.main;

import java.sql.*;

/**
 * Represent Persons.
 *
 * @version 1.0
 *
 * @author team055
 */

public class Person {
    protected String email;
    private String title;
    private String forename;
    private String surname;
    private String mobile;
    private Boolean isHost;
    private Boolean isGuest;
    private String addressLnOne;
    private String postCode;
    private String password;
    private Address address;

    // Constructors
    public Person () {}
    public Person (String email, Boolean isHost, Boolean isGuest) {
        this.email = email;
        this.isHost = isHost;
        this.isGuest = isGuest;
    }

    public Person (String email, String title, String forename, String surname, String mobile, Boolean isHost,
                   Boolean isGuest, String addressLnOne, String postCode) {
        this.email = email;
        this.title = title;
        this.forename = forename;
        this.surname = surname;
        this.mobile = mobile;
        this.isHost = isHost;
        this.isGuest = isGuest;
        this.addressLnOne = addressLnOne;
        this.postCode = postCode;
    }

    // Constructor for private information page
    public Person (String email, String forename, String surname, String mobile, String addressLnOne, String postCode) {
        this.email = email;
        this.forename = forename;
        this.surname = surname;
        this.mobile = mobile;
        this.addressLnOne = addressLnOne;
        this.postCode = postCode;
    }

    // Constructor for passing to SetRole
    public Person (String email, String title, String forename, String surname, String mobile, Address address, String password) {
        this.email = email;
        this.title = title;
        this.forename = forename;
        this.surname = surname;
        this.mobile = mobile;
        this.address = address;
        this.password = password;
    }

    // Getters
    public String getForename() {return this.forename;}
    public String getSurname() {return this.surname;}
    public String getEmail() {return this.email;}
    public String getMobile() {return mobile;}
    public String getAddressLnOne() {return addressLnOne;}
    public String getPostCode() {return postCode;}
    public Boolean getIsHost() {return isHost;}
    public Boolean getIsGuest() {return isGuest;}
    public String getPassword() {return password;}
    public String getTitle() {return title;}
    public Address getAddress() {return address;}

    // Setters
    public void setForename(String forename) {this.forename = forename;}
    public void setSurname(String surname) {this.surname = surname;}
    public void setEmail(String email) {this.email = email;}
    public void setMobile(String mobile) {this.mobile = mobile;}
    public void setAddressLnOne(String addressLnOne) {this.addressLnOne = addressLnOne;}
    public void setPostCode(String postCode) {this.postCode = postCode;}
    public void setIsGuest(int isGuest) {
        if (isGuest == 1) {
            this.isGuest = true;
        } else if (isGuest == 0) {
            this.isGuest = false;
        }
    }

    public void setIsHost(int isHost) {
        if (isHost == 1) {
            this.isHost = true;
        } else if (isHost == 0) {
            this.isHost = false;
        }
    }

    /**
     * Get chargeband associated with booking start date.
     * Only the start date is considered for setting the price for all nights.
     *
     * @param email email address of person
     * @return Name of the person retrieved from database
     */
    public String getNameFromEmail(String email){
        String baseSearchQuery = "SELECT publicName FROM guests WHERE email = ?";
        ResultSet searchResultSet;
        String returnVal = "";

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
             PreparedStatement preparedSearch = con.prepareStatement(baseSearchQuery)) {
            preparedSearch.setString(1, email);
            searchResultSet = preparedSearch.executeQuery();
            searchResultSet.next();
            returnVal = searchResultSet.getString("publicName");
        } catch (SQLException e) {
            throw new IllegalStateException("Connection Failed!", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnVal;
    }

    public boolean checkEmail(String email){
        boolean returnVal = false;
        String baseSearchQuery = "SELECT publicName FROM guests WHERE email = ?";
        ResultSet searchResultSet;
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
             PreparedStatement preparedSearch = con.prepareStatement(baseSearchQuery)) {
            preparedSearch.setString(1, email);
            searchResultSet = preparedSearch.executeQuery();
            if(searchResultSet.next()){
                returnVal = true;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Connection Failed!", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnVal;
    }

    @Override
    public String toString() {
        return "Person{" +
                "email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", isHost=" + isHost +
                ", isGuest=" + isGuest +
                ", addressLnOne='" + addressLnOne + '\'' +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
