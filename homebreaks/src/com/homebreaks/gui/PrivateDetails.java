package com.homebreaks.gui;

import com.homebreaks.main.Address;
import com.homebreaks.main.Booking;
import com.homebreaks.main.ChargeBand;
import com.homebreaks.main.Person;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.*;

/**
 * Private details window displaying confidential user information once
 * Booking has been accepted. For arranging details of visit outside of app.
 *
 * @version 1.0
 *
 * @author team055
 *
 */

public class PrivateDetails extends JFrame{
    private JFrame frame;
    private JPanel mainPanel;
    private Box propertyB = Box.createVerticalBox();
    private Box guestB = Box.createVerticalBox();
    private Box hostB = Box.createVerticalBox();
    private Box chargeB = Box.createVerticalBox();
    private Font titleFont = new Font(null, Font.BOLD, 18);

    public PrivateDetails(Booking booking, Person user, AbstractHomebreaksPanel p) {
        frame = new JFrame("Booking Information");
        mainPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        Border border = BorderFactory.createLineBorder(Color.GRAY, 2);
        Border line = BorderFactory.createLineBorder(Color.BLACK,2);
        Border margin = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(border, margin));

        Box propertyTitleB = Box.createHorizontalBox();
        propertyTitleB.add(Box.createHorizontalGlue());
        JLabel propertyTitleL = new JLabel("Property Information");
        propertyTitleL.setFont(titleFont);
        propertyTitleB.add(propertyTitleL);
        propertyTitleB.add(Box.createHorizontalGlue());
        propertyB.add(propertyTitleB);

        // Getting the confidential Property information
        String propertyName = getPropNameFromBooking(booking.getPropertyID());
        Address address = getPropAddressFromBooking(booking.getPropertyID());
        String addressLnOne = address.getAddressLnOne();
        String addressLnTwo = address.getAddressLnTwo();
        String city = address.getCity();
        String postCode = address.getPostCode();

        // Getting the confidential Guest information
        Person guest = getPersonFromEmail(booking.getEmail());
        Address guestAddress = getAddressFromEmail(booking.getEmail(), guest);

        // Getting the confidential Host information
        String hostEmail = getHostEmailFromProperty(booking.getPropertyID());
        Person host = getPersonFromEmail(hostEmail);
        Address hostAddress = getAddressFromEmail(hostEmail, host);

        Box propertyInfoB = Box.createHorizontalBox();
        String addTwo="";
        if (addressLnTwo.length()>0)addTwo=addressLnTwo+"<br>";
        JLabel propertyL = new JLabel(
            "<html><body style='width: 450'>"
                + "<strong>Name: </strong>" + propertyName + "<br>"
                + "<strong>Address:</strong><br>"
                + addressLnOne + "<br>"
                + addTwo
                + city + "<br>"
                + postCode + "<br>"
                + "</body></html>");
        propertyInfoB.add(propertyL);
        propertyInfoB.add(Box.createHorizontalGlue());
        propertyB.add(propertyInfoB);
        propertyB.setBorder(BorderFactory.createCompoundBorder(line, margin));

        Box guestTitleB = Box.createHorizontalBox();
        guestTitleB.add(Box.createHorizontalGlue());
        JLabel guestTitleL = new JLabel("Guest Information");
        guestTitleL.setFont(titleFont);
        guestTitleB.add(guestTitleL);
        guestTitleB.add(Box.createHorizontalGlue());
        guestB.add(guestTitleB);

        Box guestInfoB = Box.createHorizontalBox();
        JLabel guestL = new JLabel("<html><body style='width: 450'>"
                + "<strong>Name: </strong>" + guest.getForename() + " " + guest.getSurname()
                + "<br>" + "<strong>Phone: </strong>" + guest.getMobile()
                + "<br>" + "<strong>Email: </strong>" + guest.getEmail()
                + "</body></html>");

        guestInfoB.add(guestL);
        guestInfoB.add(Box.createHorizontalGlue());
        guestB.add(guestInfoB);
        guestB.setBorder(BorderFactory.createCompoundBorder(line, margin));

        Box hostTitleB = Box.createHorizontalBox();
        hostTitleB.add(Box.createHorizontalGlue());
        JLabel hostTitleL = new JLabel("Host Information");
        hostTitleL.setFont(titleFont);
        hostTitleB.add(hostTitleL);
        hostTitleB.add(Box.createHorizontalGlue());
        hostB.add(hostTitleB);

        Box hostInfoB = Box.createHorizontalBox();
        JLabel hostL = new JLabel("<html><body style='width: 450'>"
                + "<strong>Name: </strong>" + host.getForename() + " " + host.getSurname()
                + "<br>" + "<strong>Phone: </strong>" + host.getMobile()
                + "<br>" + "<strong>Email: </strong>" + host.getEmail()
                + "</body></html>");
        hostInfoB.add(hostL);
        hostInfoB.add(Box.createHorizontalGlue());
        hostB.add(hostInfoB);
        hostB.setBorder(BorderFactory.createCompoundBorder(line, margin));

        mainPanel.add(propertyB);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(guestB);
        mainPanel.add(hostB);

        System.out.println(user.getIsGuest());
        System.out.println(user.getIsHost());
        if (user.getIsGuest() && p.switchRoleFlag == 0) {
            hostB.setVisible(true);
            guestB.setVisible(false);
        } else {
            hostB.setVisible(false);
            guestB.setVisible(true);
        }

        frame.add(scrollPane);
        frame.setPreferredSize(new Dimension(600,350));
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the form
        frame.setVisible(true);
    }

    /**
     * Get property name from its ID
     *
     * @param propertyID ID of property
     * @return name of property
     */
    public String getPropNameFromBooking(int propertyID) {
        String query = "SELECT name FROM properties WHERE propertyID = ?";
        String name = "";
        try (Connection con = DriverManager.getConnection
                ("jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365")) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, propertyID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * Get property address from its ID
     *
     * @param propertyID ID of property
     * @return address of property
     */
    public Address getPropAddressFromBooking(int propertyID) {
        String query = "SELECT addressLnOne, addressLnTwo, city, postCode FROM properties WHERE propertyID = ?";
        String addressLnOne = "";
        String addressLnTwo = "";
        String city = "";
        String postCode = "";
        Address address = new Address();

        try (Connection con = DriverManager.getConnection
                ("jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365")) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, propertyID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addressLnOne = rs.getString("addressLnOne");
                addressLnTwo = rs.getString("addressLnTwo");
                city = rs.getString("city");
                postCode = rs.getString("postCode");
            }
            address.setAddressLnOne(addressLnOne);
            address.setAddressLnTwo(addressLnTwo);
            address.setCity(city);
            address.setPostCode(postCode);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }

    /**
     * Get host email from propertyID
     *
     * @param propertyID ID of property
     * @return email of host
     */
    public String getHostEmailFromProperty(int propertyID) {
        String query = "SELECT email FROM properties WHERE propertyID = ?";
        String email = "";
        try (Connection con = DriverManager.getConnection
                ("jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365")) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, propertyID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                email = rs.getString("email");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }

    /**
     * Get Person object from email address
     *
     * @param email email of person
     * @return person object
     */
    public Person getPersonFromEmail (String email) {
        String query = "SELECT forename, surname, mobile, addressLnOne, postCode FROM persons WHERE email = ?";
        String forename = "";
        String surname = "";
        String mobile = "";
        String addressLnOne = "";
        String postCode = "";
        Person person = new Person();

        try (Connection con = DriverManager.getConnection
                ("jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365")) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                forename = rs.getString("forename");
                surname = rs.getString("surname");
                mobile = rs.getString("mobile");
                addressLnOne = rs.getString("addressLnOne");
                postCode = rs.getString("postCode");
            }
            person.setEmail(email);
            person.setForename(forename);
            person.setSurname(surname);
            person.setMobile(mobile);
            person.setAddressLnOne(addressLnOne);
            person.setPostCode(postCode);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    /**
     * Get Address object from email address
     *
     * @param email email of person
     * @param person person to get address for
     * @return address of person
     */
    public Address getAddressFromEmail (String email, Person person) {
        String query = "SELECT * FROM addresses WHERE addressLnOne = ? AND postCode = ?";
        String addressLnOne = person.getAddressLnOne();
        String addressLnTwo = "";
        String city = "";
        String postCode = person.getPostCode();
        Address address = new Address();

        try (Connection con = DriverManager.getConnection
                ("jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365")) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, addressLnOne);
            ps.setString(2, postCode);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addressLnTwo = rs.getString("addressLnTwo");
                city = rs.getString("city");
            }
            address.setAddressLnOne(addressLnOne);
            address.setAddressLnTwo(addressLnTwo);
            address.setCity(city);
            address.setPostCode(postCode);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }
}

