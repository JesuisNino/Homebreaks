package com.homebreaks.main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Represents Sleeping facility
 *
 * @version 1.0
 *
 * @author team055
 *
 */

public class Sleeping {
    private int hasTowel;
    private int hasLinen;
    private List<Bedroom> bedrooms = new ArrayList<>();

    public Sleeping () {}

    public Sleeping(int hasTowel, int hasLinen) {
        this.hasTowel = hasTowel;
        this.hasLinen = hasLinen;
    }

    public Sleeping(int hasTowel, int hasLinen, List<Bedroom> bedrooms) {
        this.bedrooms = bedrooms;
        this.hasTowel = hasTowel;
        this.hasLinen = hasLinen;
    }

    public void setHasTowel(int hasTowel) {this.hasTowel = hasTowel;}
    public int getHasTowel() {return hasTowel;}
    public void setHasLinen(int hasLinen) {this.hasLinen = hasLinen;}
    public int getHasLinen() {return hasLinen;}
    public void setBedrooms(List<Bedroom> bedrooms) {this.bedrooms = bedrooms;}
    public List<Bedroom> getBedrooms() {return bedrooms;}

    public void addBedroom(Bedroom bedroom) { this.bedrooms.add(bedroom); }
    public int getBedroomCount() { return bedrooms.size(); }

    public int getBedCount() {
        int i = 0;
        for (Bedroom bedroom : bedrooms) {
            i += bedroom.getBedCount();
        }
        return i;
    }

    public int getMaxGuests() {
        int totalGuests = 0;
        for (Bedroom bedroom : bedrooms) {
            totalGuests += bedroom.calculateNumberGuests();
        }
        return totalGuests;
    }

    public static List<Bedroom> getBedroomsFromPropId(int propertyID) {
        String baseSearchQuery = "SELECT * FROM bedrooms WHERE propertyID = ?";
        ResultSet searchResultSet;
        List<Bedroom> bedroomList = new ArrayList<Bedroom>();

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
             PreparedStatement preparedSearch = con.prepareStatement(baseSearchQuery)) {
            preparedSearch.setInt(1, propertyID);
            searchResultSet = preparedSearch.executeQuery();
            while(searchResultSet.next()) {
                Bedroom bedroom = new Bedroom(searchResultSet.getInt("bedOne"),
                        searchResultSet.getInt("bedTwo"));
                bedroomList.add(bedroom);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Connection Failed!", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bedroomList;
    }

    @Override
    public String toString() {
        return "Sleeping{" +
                "hasTowel=" + hasTowel +
                ", hasLinen=" + hasLinen +
                ", bedrooms=" + bedrooms +
                '}';
    }


}
