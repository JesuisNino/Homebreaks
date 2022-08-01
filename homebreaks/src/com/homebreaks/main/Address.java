package com.homebreaks.main;

/**
 * Represents addresses of users
 *
 * @version 1.0
 *
 * @author team055
 *
 */

public class Address {
    private String addressLnOne;
    private String addressLnTwo;
    private String city;
    private String postCode;

    // Constructors
    public Address () {}

    public Address (String addressLnOne, String addressLnTwo, String city, String postCode) {
        this.addressLnOne = addressLnOne;
        this.addressLnTwo = addressLnTwo;
        this.city = city;
        this.postCode = postCode;
    }

    // Getters
    public String getAddressLnOne() { return addressLnOne; }
    public String getAddressLnTwo() { return addressLnTwo; }
    public String getCity() { return city; }
    public String getPostCode() { return postCode; }

    // Setters
    public void setCity(String city) { this.city = city; }
    public void setAddressLnOne(String addressLnOne) { this.addressLnOne = addressLnOne; }
    public void setAddressLnTwo(String addressLnTwo) { this.addressLnTwo = addressLnTwo; }
    public void setPostCode(String postCode) { this.postCode = postCode; }

    @Override
    public String toString() {
        return "Address{" +
                "addressLnOne='" + addressLnOne + '\'' +
                ", addressLnTwo='" + addressLnTwo + '\'' +
                ", city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
