package com.homebreaks.util;

/**
 * Initialise the database.
 *
 * @version 1.0 05.12.2022
 *
 * @author team055
 *
 */

public class InitDatabase {

    public static String deleteAddresses = "DROP TABLE addresses;";
    public static String createAddresses =
            "CREATE TABLE addresses (" +
                "addressLnOne VARCHAR (100)," +
                "addressLnTwo VARCHAR (100)," +
                "city VARCHAR (100)," +
                "postCode VARCHAR (10)," +
                "PRIMARY KEY (addressLnOne, postCode));";

    public static String deletePersons = "DROP TABLE persons;";
    public static String createPersons =
            "CREATE TABLE persons (" +
                "email VARCHAR (100) NOT NULL PRIMARY KEY," +
                "title VARCHAR(10)," +
                "forename VARCHAR (25)," +
                "surname VARCHAR (50)," +
                "mobile VARCHAR (30)," +
                "password VARCHAR (255) NOT NULL," +
                "salt VARCHAR (20) NOT NULL," +
                "isHost TINYINT(1)," +
                "isGuest TINYINT(1)," +
                "addressLnOne VARCHAR (100)," +
                "postCode VARCHAR (10)," +
                "CONSTRAINT `fk_person_to_address`" +
                    " FOREIGN KEY (addressLnOne, postCode) REFERENCES addresses(addressLnOne, postCode)" +
                    " ON DELETE CASCADE" +
                    " ON UPDATE RESTRICT);";

    public static String deleteProp = "DROP TABLE properties;";
    public static String createProp =
            "CREATE TABLE properties (" +
                "propertyID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100)," +
                "description VARCHAR (1000)," +
                "location VARCHAR (100)," +
                "addressLnOne VARCHAR (100)," +
                "addressLnTwo VARCHAR (100)," +
                "city VARCHAR (100)," +
                "postCode VARCHAR (12)," +
                "hasBreakfast TINYINT(1)," +
                "hasTowel TINYINT(1)," +
                "hasLinen TINYINT(1)," +
                "hasHairDrier TINYINT(1)," +
                "hasShampoo TINYINT(1)," +
                "hasToiletPaper TINYINT(1)," +
                "hasFridge TINYINT(1)," +
                "hasMicro TINYINT(1)," +
                "hasOven TINYINT(1)," +
                "hasStove TINYINT(1)," +
                "hasDishwasher TINYINT(1)," +
                "hasTableware TINYINT(1)," +
                "hasCookware TINYINT(1)," +
                "hasBasics TINYINT(1)," +
                "hasWLAN TINYINT(1)," +
                "hasTV TINYINT(1)," +
                "hasSatellite TINYINT(1)," +
                "hasStreaming TINYINT(1)," +
                "hasDVD TINYINT(1)," +
                "hasBoardGames TINYINT(1)," +
                "hasHeating TINYINT(1)," +
                "hasWasher TINYINT(1)," +
                "hasClothesDrier TINYINT(1)," +
                "hasExtinguisher TINYINT(1)," +
                "hasSmokeAlarm TINYINT(1)," +
                "hasFirstAid TINYINT(1)," +
                "hasOnSiteParking TINYINT(1)," +
                "hasRoadParking TINYINT(1)," +
                "hasCarPark TINYINT(1)," +
                "hasPatio TINYINT(1)," +
                "hasBBQ TINYINT(1)," +
                "email VARCHAR (100) NOT NULL," +
                "CONSTRAINT `fk_property_to_person`" +
                    " FOREIGN KEY (email) REFERENCES persons(email)" +
                    " ON DELETE CASCADE" +
                    " ON UPDATE RESTRICT);";

    public static String deleteChargeband = "DROP TABLE chargebands;";
    public static String createChargeband =
            "CREATE TABLE chargebands (" +
                "chargebandID INT NOT NULL AUTO_INCREMENT," +
                "startDate DATE," +
                "endDate DATE," +
                "pricePerNight FLOAT," +
                "serviceCharge FLOAT," +
                "cleaningCharge FLOAT," +
                "propertyID INT," +
                "PRIMARY KEY(chargebandID)," +
                "CONSTRAINT `fk_chargeband_to_property`" +
                    " FOREIGN KEY (propertyID) REFERENCES properties(propertyID)" +
                    " ON DELETE CASCADE" +
                    " ON UPDATE RESTRICT);";

    public static String deleteHost = "DROP TABLE hosts;";
    public static String createHost =
            "CREATE TABLE hosts (" +
                    "publicName VARCHAR (100)," +
                    "isSuperHost TINYINT(1)," +
                    "email VARCHAR (100)," +
                    "PRIMARY KEY(email)," +
                    "CONSTRAINT `fk_host_to_person`" +
                    " FOREIGN KEY (email) REFERENCES persons(email)" +
                    " ON DELETE CASCADE" +
                    " ON UPDATE RESTRICT);";

    public static String deleteGuest = "DROP TABLE guests;";
    public static String createGuest =
            "CREATE TABLE guests (" +
                    "publicName VARCHAR (100)," +
                    "email VARCHAR (100)," +
                    "PRIMARY KEY(email)," +
                    "CONSTRAINT `fk_guest_to_person`" +
                    " FOREIGN KEY (email) REFERENCES persons(email)" +
                    " ON DELETE CASCADE" +
                    " ON UPDATE RESTRICT);";


    public static String deleteBedroom = "DROP TABLE bedrooms;";
    public static String createBedroom =
            "CREATE TABLE bedrooms (" +
                    "bedroomID INT NOT NULL AUTO_INCREMENT," +
                    "bedOne TINYINT (2)," +
                    "bedTwo TINYINT (2)," +
                    "propertyID INT," +
                    "PRIMARY KEY(bedroomID)," +
                    "CONSTRAINT `fk_bedroom_to_property`" +
                    " FOREIGN KEY (propertyID) REFERENCES properties(propertyID)" +
                    " ON DELETE CASCADE" +
                    " ON UPDATE RESTRICT);";

    public static String deleteBathroom = "DROP TABLE bathrooms;";
    public static String createBathroom =
            "CREATE TABLE bathrooms (" +
                    "bathroomID INT NOT NULL AUTO_INCREMENT," +
                    "hasToilet TINYINT (1)," +
                    "hasBath TINYINT (1)," +
                    "hasShower TINYINT (1)," +
                    "isShared TINYINT (1)," +
                    "propertyID INT," +
                    "PRIMARY KEY(bathroomID)," +
                    "CONSTRAINT `fk_bathroom_to_property`" +
                    " FOREIGN KEY (propertyID) REFERENCES properties(propertyID)" +
                    " ON DELETE CASCADE" +
                    " ON UPDATE RESTRICT);";

    public static String deleteBooking = "DROP TABLE bookings;";
    public static String createBooking =
            "CREATE TABLE bookings (" +
                    "bookingID INT NOT NULL AUTO_INCREMENT," +
                    "startDateBooking DATE," +
                    "endDateBooking DATE," +
                    "totalPrice FLOAT," +
                    "PRIMARY KEY(bookingID)," +
                    "bookingStatus VARCHAR(20)," +
                    "propertyID INT," +
                    "email VARCHAR (100));";

    public static String deleteReview = "DROP TABLE reviews;";
    public static String createReview =
            "CREATE TABLE reviews (" +
                    "reviewID INT NOT NULL AUTO_INCREMENT," +
                    "description VARCHAR (500)," +
                    "cleanliness TINYINT(4)," +
                    "communication TINYINT(4)," +
                    "checkin TINYINT(4)," +
                    "accuracy TINYINT(4)," +
                    "location TINYINT(4)," +
                    "value TINYINT(4)," +
                    "PRIMARY KEY(reviewID)," +
                    "propertyID INT," +
                    "email VARCHAR (100));";

    public static String addBookingFKOne =
                    "ALTER TABLE bookings" +
                    " ADD CONSTRAINT `fk_booking_guest`" +
                    " FOREIGN KEY (email) REFERENCES guests(email);";

    public static String addBookingFKTwo =
            "ALTER TABLE bookings" +
                    " ADD CONSTRAINT `fk_booking_property`" +
                    " FOREIGN KEY (propertyID) REFERENCES properties(propertyID);";

    public static String addReviewFKOne =
            "ALTER TABLE reviews" +
                    " ADD CONSTRAINT `fk_review_guest`" +
                    " FOREIGN KEY (email) REFERENCES guests(email);";

    public static String addReviewFKTwo =
            "ALTER TABLE reviews" +
                    " ADD CONSTRAINT `fk_review_property`" +
                    " FOREIGN KEY (propertyID) REFERENCES properties(propertyID);";

    public static void main(String[] args) {
        // DELETE
        DatabaseConnector deleteChargeTable = new DatabaseConnector();
        deleteChargeTable.executeQuery((deleteChargeband));

        DatabaseConnector deleteBathroomTable = new DatabaseConnector();
        deleteBathroomTable.executeQuery((deleteBathroom));

        DatabaseConnector deleteBedroomTable = new DatabaseConnector();
        deleteBedroomTable.executeQuery((deleteBedroom));

        DatabaseConnector deleteBookingTable = new DatabaseConnector();
        deleteBookingTable.executeQuery((deleteBooking));

        DatabaseConnector deleteReviewTable = new DatabaseConnector();
        deleteReviewTable.executeQuery((deleteReview));

        DatabaseConnector deletePropTable = new DatabaseConnector();
        deletePropTable.executeQuery((deleteProp));

        DatabaseConnector deleteHostTable = new DatabaseConnector();
        deleteHostTable.executeQuery((deleteHost));

        DatabaseConnector deleteGuestTable = new DatabaseConnector();
        deleteGuestTable.executeQuery((deleteGuest));

        DatabaseConnector deleteUserTable = new DatabaseConnector();
        deleteUserTable.executeQuery((deletePersons));

        DatabaseConnector deleteAddressTable = new DatabaseConnector();
        deleteAddressTable.executeQuery((deleteAddresses));


        // CREATE
        DatabaseConnector createAddressTable = new DatabaseConnector();
        createAddressTable.executeQuery((createAddresses));

        DatabaseConnector createUserTable = new DatabaseConnector();
        createUserTable.executeQuery((createPersons));

        DatabaseConnector createHostTable = new DatabaseConnector();
        createHostTable.executeQuery((createHost));

        DatabaseConnector createGuestTable = new DatabaseConnector();
        createGuestTable.executeQuery((createGuest));

        DatabaseConnector createPropTable = new DatabaseConnector();
        createPropTable.executeQuery((createProp));

        DatabaseConnector createBookingTable = new DatabaseConnector();
        createBookingTable.executeQuery((createBooking));

        DatabaseConnector createReviewTable = new DatabaseConnector();
        createReviewTable.executeQuery((createReview));

        DatabaseConnector createChargebandTable = new DatabaseConnector();
        createChargebandTable.executeQuery((createChargeband));

        DatabaseConnector createBedroomTable = new DatabaseConnector();
        createBedroomTable.executeQuery((createBedroom));

        DatabaseConnector createBathroomTable = new DatabaseConnector();
        createBathroomTable.executeQuery((createBathroom));

        DatabaseConnector addBookingFKTableOne = new DatabaseConnector();
        addBookingFKTableOne.executeQuery((addBookingFKOne));

        DatabaseConnector addBookingFKTableTwo = new DatabaseConnector();
        addBookingFKTableTwo.executeQuery((addBookingFKTwo));

        DatabaseConnector addReviewFKTableOne = new DatabaseConnector();
        addReviewFKTableOne.executeQuery((addReviewFKOne));

        DatabaseConnector addReviewFKTableTwo = new DatabaseConnector();
        addReviewFKTableTwo.executeQuery((addReviewFKTwo));
    }
}
