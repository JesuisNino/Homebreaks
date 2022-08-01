package com.homebreaks.gui;

import com.homebreaks.main.Booking;
import com.homebreaks.main.Person;
import com.homebreaks.main.Property;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingSearch {

    public List<Booking> bookingSearch(Person user){
        List<Booking> bookingList = new ArrayList<Booking>();
        String baseSearchQuery = "SELECT * FROM bookings WHERE bookingStatus = ? AND email = ?";
        ResultSet searchResultSet;
        String[] statusList = {"Pending","Accepted","Rejected"};

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
             PreparedStatement preparedSearch = con.prepareStatement(baseSearchQuery)) {
            for(int y =0; y<3; y++) {
                preparedSearch.setString(1, statusList[y]);
                preparedSearch.setString(2, user.getEmail());
                searchResultSet = preparedSearch.executeQuery();
                Booking book;
                while (searchResultSet.next()) {
                    int bookingID = searchResultSet.getInt("bookingID");
                    Date startDateBooking = searchResultSet.getDate("startDateBooking");
                    Date endDateBooking = searchResultSet.getDate("endDateBooking");
                    Double totalPrice = searchResultSet.getDouble("totalPrice");
                    String bookingStatus = searchResultSet.getString("bookingStatus");
                    int propertyID = searchResultSet.getInt("propertyID");
                    String email = searchResultSet.getString("email");

                    book = new Booking(bookingID, startDateBooking, endDateBooking, totalPrice, bookingStatus, propertyID, email);
                    bookingList.add(book);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Connection Failed!", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingList;
    }

    /**
     * Gets list of bookings for host.
     *
     * @param user host user person
     * @return list of bookings associated with host
     */
    public List<Booking> hostBookingSearch(Person user){
        Property IDGetter = new Property();
        List<Integer> IDList = IDGetter.getIDsFromEmail(user.getEmail());
        List<Booking> bookingList = new ArrayList<Booking>();
        String baseSearchQuery = "SELECT * FROM bookings WHERE bookingStatus = ? AND propertyID = ?";
        ResultSet searchResultSet;
        String[] statusList = {"Pending","Accepted","Rejected"};
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
             PreparedStatement preparedSearch = con.prepareStatement(baseSearchQuery)) {
            for(int y =0; y<3; y++){
                preparedSearch.setString(1,statusList[y]);
        for (int x : IDList) {
            preparedSearch.setInt(2, x);
            searchResultSet = preparedSearch.executeQuery();
            Booking book;
            while (searchResultSet.next()) {
                int bookingID = searchResultSet.getInt("bookingID");
                Date startDateBooking = searchResultSet.getDate("startDateBooking");
                Date endDateBooking = searchResultSet.getDate("endDateBooking");
                Double totalPrice = searchResultSet.getDouble("totalPrice");
                String bookingStatus = searchResultSet.getString("bookingStatus");
                int propertyID = searchResultSet.getInt("propertyID");
                String email = searchResultSet.getString("email");

                book = new Booking(bookingID, startDateBooking, endDateBooking, totalPrice, bookingStatus, propertyID, email);
                bookingList.add(book);
            }
        }
        }
        } catch (SQLException e) {
            throw new IllegalStateException("Connection Failed!", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingList;
    }

}
