package com.homebreaks.gui;

import com.homebreaks.main.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class Search {

    private static final String SQL_SelectProperty = "SELECT * FROM properties";

    /**
     * Get search results based on user parameters
     *
     * @param locationGot email of person
     * @param startGot Start date from the date picker in search
     * @param endGot End dates from the date picker in search
     * @param guestsGot Amount of max guests from search
     * @param person person to get address for
     * @return list of properties satisfying the search parameters
     */
    public static List<Property> getPropertySearch
            (String locationGot, String startGot, String endGot, String guestsGot) {
        List<Property> filteredProp = new ArrayList<>();

        Integer queryCounter = 0;
        String locationSearch = "";
        int[] dateSearch;
        String guestsSearch = "";
        Boolean searchByLocation = false;
        Boolean searchByStart = false;
        Boolean searchByEnd = false;
        Boolean searchByGuests = false;
        Boolean firstTermDone = false;

        String query = "select * from properties";

        if (!locationGot.equals("Any")) {
            queryCounter += 1;
            searchByLocation = true;
        }
        if (!startGot.equals("Any")) {
            searchByStart = true;
        }
        if (!endGot.equals("Any")) {
            searchByEnd = true;
        }
        if (!guestsGot.equals("Any")) {
            searchByGuests = true;
            if (guestsGot.equalsIgnoreCase("7+")){
                guestsGot = "7";
            }
        }

        if (queryCounter > 0) {

            query = query + " where ";

            if (searchByLocation) {
                query = query + "upper(location) like upper(?)";
                firstTermDone = true;
            }


        }
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            if (queryCounter > 0) {
                Integer prepared = 1;

                if (searchByLocation && prepared <= queryCounter) {
                    preparedStatement.setString(prepared, "%" + locationGot + "%");
                    prepared += 1;
                }
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int propertyID = resultSet.getInt("propertyID");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                int hasBreakfast = resultSet.getInt("hasBreakfast");
                String addressLnOne = resultSet.getString("addressLnOne");
                String addressLnTwo = resultSet.getString("addressLnTwo");
                String city = resultSet.getString("city");
                String postCode = resultSet.getString("postCode");
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

                // Construct various objects and the property
                // Note that the bathrooms and bedrooms are not yet included
                Address address = new Address(addressLnOne, addressLnTwo, city, postCode);
                Sleeping sleeping = new Sleeping(hasTowel, hasLinen, Sleeping.getBedroomsFromPropId(propertyID));
                Bathing bathing = new Bathing(hasHairDrier, hasShampoo, hasToiletPaper);
                Kitchen kitchen = new Kitchen(hasFridge, hasMicro, hasOven, hasStove, hasDishwasher, hasTableware,
                        hasCookware, hasBasics);
                Living living = new Living(hasWLAN, hasTV, hasSatellite, hasStreaming, hasDVD, hasBoardGames);
                Utility utility = new Utility(hasHeating, hasWasher, hasClothesDrier, hasExtinguisher, hasSmokeAlarm,
                        hasFirstAid);
                Outdoors outdoors = new Outdoors(hasOnSiteParking, hasRoadParking, hasCarPark, hasPatio, hasBBQ);
                Property prop = new Property(propertyID, email, name, description, location, hasBreakfast, address, sleeping, bathing,
                        kitchen, living, utility, outdoors);

                if(searchByGuests) {
                    if (prop.getSleeping().getMaxGuests() >= Integer.parseInt(guestsGot)) {
                        filteredProp.add(prop);
                    }
                }
                else{
                    filteredProp.add(prop);
                }

            }

        } catch (SQLException e) {
            throw new IllegalStateException("Connection Failed!", e);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!startGot.equals("Any")&&!endGot.equals("Any")){
            String baseDateQuery = "SELECT * FROM bookings where bookingStatus = 'accepted' and startDateBooking <= ? and endDateBooking >= ? and propertyID = ?";
            List<Property> dateFilteredProp = new ArrayList<>();

            ResultSet dateResultSet;
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
                 PreparedStatement preparedDate = con.prepareStatement(baseDateQuery)) {

                preparedDate.setDate(1, Date.valueOf(endGot));
                preparedDate.setDate(2, Date.valueOf(startGot));
            for (Property property : filteredProp) {
                preparedDate.setInt(3, property.getPropertyID());
                dateResultSet = preparedDate.executeQuery();
                if (!dateResultSet.next()){
                    dateFilteredProp.add(property);
                }
            }
            } catch (SQLException e) {
                throw new IllegalStateException("Connection Failed!", e);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dateFilteredProp;
        }
        else {
            return filteredProp;
        }
    }
}
