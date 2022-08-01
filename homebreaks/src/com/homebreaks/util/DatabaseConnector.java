package com.homebreaks.util;

import com.homebreaks.main.Property;
import java.sql.*;

/**
 * Helper class for creating database connections, used for adding dummy data
 *
 * @version 1.0 05.12.2022
 *
 * @author team055
 *
 */

public class DatabaseConnector {
    private final String URL = "jdbc:mysql://stusql.dcs.shef.ac.uk/team055";
    private final String USERNAME = "team055";
    private final String PASSWORD = "c471f365";

    public void executeQuery(String query) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            System.out.println("Connection successful!");
            PreparedStatement ps = conn.prepareStatement(query);
            ps.executeUpdate(query);
        } catch (SQLException e) {
            throw new IllegalStateException("Connection Failed!", e);
        }
    }

    public Property getProperty(int propID) {
        String propQuery = "SELECT * FROM properties WHERE propertyID = '" + propID + "';";
        Property prop = new Property();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            System.out.println("Getting property...");
            PreparedStatement preparedStatement = connection.prepareStatement(propQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int propertyID = resultSet.getInt("propertyID");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                String addressLnOne = resultSet.getString("addressLnOne");
                String addressLnTwo = resultSet.getString("addressLnTwo");
                String city = resultSet.getString("city");
                String postCode = resultSet.getString("postCode");
                int hasBreakfast = resultSet.getInt("hasBreakfast");
                int bedCount = resultSet.getInt("bedCount");
                int bedroomCount = resultSet.getInt("bedroomCount");
                int maxGuests = resultSet.getInt("maxGuests");
                int bathroomCount = resultSet.getInt("bathroomCount");
                double cleanlinessScore = resultSet.getDouble("cleanlinessScore");
                double communicationScore = resultSet.getDouble("communicationScore");
                double checkInScore = resultSet.getDouble("checkinScore");
                double accuracyScore = resultSet.getDouble("accuracyScore");
                double locationScore = resultSet.getDouble("locationScore");
                double valueScore = resultSet.getDouble("valueScore");
                int hasTowel = resultSet.getInt("hasTowel");
                int hasLinen = resultSet.getInt("hasLinen");
                int hasHairDrier = resultSet.getInt("hasHairDrier");
                int hasShampoo = resultSet.getInt("hasShampoo");
                int hasToiletPaper = resultSet.getInt("hasToiletPaper");
                int hasFridge = resultSet.getInt("hasFridge");
                int hasMicro = resultSet.getInt("hasMicro");
                int hasOven = resultSet.getInt("hasOven");
                int hasStove = resultSet.getInt("hasStove");
                int hasDishwasher = resultSet.getInt("hasDishwasher");
                int hasTableware = resultSet.getInt("hasTableware");
                int hasCookware = resultSet.getInt("hasCookware");
                int hasBasics = resultSet.getInt("hasBasics");
                int hasWLAN = resultSet.getInt("hasWLAN");
                int hasTV = resultSet.getInt("hasTV");
                int hasSatellite = resultSet.getInt("hasSatellite");
                int hasStreaming = resultSet.getInt("hasStreaming");
                int hasDVD = resultSet.getInt("hasDVD");
                int hasBoardGames = resultSet.getInt("hasBoardGames");
                int hasHeating = resultSet.getInt("hasHeating");
                int hasWasher = resultSet.getInt("hasWasher");
                int hasClothesDrier = resultSet.getInt("hasClothesDrier");
                int hasExtinguisher = resultSet.getInt("hasExtinguisher");
                int hasSmokeAlarm = resultSet.getInt("hasSmokeAlarm");
                int hasFirstAid = resultSet.getInt("hasFirstAid");
                int hasOnSiteParking = resultSet.getInt("hasOnSiteParking");
                int hasRoadParking = resultSet.getInt("hasRoadParking");
                int hasCarPark = resultSet.getInt("hasCarPark");
                int hasPatio = resultSet.getInt("hasPatio");
                int hasBBQ = resultSet.getInt("hasBBQ");
                String email = resultSet.getString("email");

                // ToDO: Constructor
                prop = new Property();
//                prop = new Property (propertyID, name, description, location, addressLnOne, addressLnTwo, city,
//                        postCode,  hasBreakfast,  bedCount,  bedroomCount, maxGuests, bathroomCount, cleanlinessScore,
//                        communicationScore,  checkInScore, accuracyScore, locationScore, valueScore, hasTowel, hasLinen,
//                        hasHairDrier, hasShampoo, hasToiletPaper, hasFridge, hasMicro, hasOven, hasStove, hasDishwasher,
//                        hasTableware, hasCookware, hasBasics, hasWLAN, hasTV, hasSatellite, hasStreaming, hasDVD,
//                        hasBoardGames, hasHeating, hasWasher, hasClothesDrier, hasExtinguisher, hasSmokeAlarm,
//                        hasFirstAid,  hasOnSiteParking, hasRoadParking,  hasCarPark,  hasPatio,  hasBBQ,  email);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Connection Failed!", e);
        }
        return prop;
    }
}
