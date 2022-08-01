package com.homebreaks.gui;

import com.homebreaks.main.Booking;
import com.homebreaks.main.Person;
import com.homebreaks.main.Property;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Implemented main glass for GUI, initalising GUI components
 *
 * @version 1.0
 *
 * @author team055
 *
 */

public class HomebreaksPanel extends AbstractHomebreaksPanel {
    public HomebreaksPanel(Person person) {
        super(person);
    }

    public void setUser (Person user) {
        this.user = user;
    }

    /**
     * Populate and build the search results
     *
     * @param location search string for property location, matches sub-strings
     * @param start search string for available start date
     * @param end search string for available end date
     * @param end search string for number of guests
     * @return UI scrollPane component with search results
     */
    public JScrollPane getResult(String location, String start, String end, String guests) {
        // Show the list includes the result after click the search button
        JPanel result = new JPanel();
        JScrollPane scrollPane = new JScrollPane(result);

        // Set borders
        Border border1 = BorderFactory.createLineBorder(Color.BLACK, 2);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(border1, "Search results");
        titledBorder.setTitleFont(new Font(null,Font.BOLD,14));
        Border propBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
        Border margin = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        // Apply borders and layout to the scrollpane
        result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
        scrollPane.setPreferredSize(new Dimension(1000, 650)); // Can set depending on the screen
        scrollPane.setBorder(BorderFactory.createCompoundBorder(titledBorder, margin));

        // Get results
        allProperties = Search.getPropertySearch(location, start, end, guests);
        for (Property p : allProperties) {
            int propertyID = p.getPropertyID();
            JButton detailsBtn = new JButton("More Details");
            detailsBtn.setFont(new Font(null,Font.BOLD,14));
            // Create each property
            Box prop = Box.createHorizontalBox();
            prop.setBorder(BorderFactory.createCompoundBorder(propBorder, margin));
            JLabel room = new JLabel
                    ("<html><body>" + "<strong>" + p.getName() + "</strong>" + "<br>"
                            + p.getLocation() + "</body></html>");
            prop.add(room);
            prop.add(Box.createHorizontalGlue());
            prop.add(Box.createRigidArea(new Dimension(100, 10)));
            prop.add(Box.createHorizontalGlue());
            prop.add(detailsBtn);

            // Add one result to the result list and set the UI format
            result.add(prop);
            result.add(Box.createRigidArea(new Dimension(100, 10)));

            // Details frame
            detailsBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame detailsFrame = new Detail(p, user, switchRoleFlag);
                }
            });
        }
        return scrollPane;
    }

    /**
     * Populate and build the booking results
     *
     * @return UI scrollPane component with search results
     */
    public JScrollPane getBooking() {
        JPanel result = new JPanel();
        JScrollPane scrollPane = new JScrollPane(result);

        // Set borders
        Border border1 = BorderFactory.createLineBorder(Color.BLACK, 2);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(border1, "Booking list");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        titledBorder.setTitleFont(new Font(null,Font.BOLD,18));
        Border propBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
        Border margin = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        // Apply borders and layout to the scrollpane
        result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
        scrollPane.setPreferredSize(new Dimension(1000, 650)); // Can set depending on the screen
        scrollPane.setBorder(BorderFactory.createCompoundBorder(titledBorder, margin));
        BookingSearch bookingSearcher = new BookingSearch();
        Property nameGetter = new Property();

        // No bookings box
        Box noBookingsB = Box.createHorizontalBox();
        JLabel noBookingsL = new JLabel("There are currently no bookings to display.");
        noBookingsB.add(noBookingsL);
        noBookingsB.add(Box.createHorizontalGlue());
        result.add(noBookingsB);
        noBookingsB.setVisible(false);

        if (user.getIsGuest() && switchRoleFlag == 0) {
            bookings = bookingSearcher.bookingSearch(user);
        } else {
            bookings = bookingSearcher.hostBookingSearch(user);
        }

        if (bookings.isEmpty()) {
            noBookingsB.setVisible(true);
        } else {
            noBookingsB.setVisible(false);
        }

        for(Booking b : bookings) {
            Box prop = Box.createHorizontalBox();
            prop.setBorder(BorderFactory.createCompoundBorder(propBorder, margin));
            Person personNameGetter = new Person();

            // Format the dates nicely
            LocalDate localStartDate = b.getStartDateBooking().toLocalDate();
            LocalDate localEndDate = b.getEndDateBooking().toLocalDate();
            String formattedStartDate = localStartDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
            String formattedEndDate = localEndDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));

            // Set the colour for the various statuses
            String statusColour ="";
            if (b.getBookingStatus().equals("Pending")) { statusColour = "<span style=\"color:green\">"; }
            else if (b.getBookingStatus().equals("Accepted")) { statusColour = "<span style=\"color:blue\">"; }
            else if (b.getBookingStatus().equals("Rejected")) { statusColour = "<span style=\"color:red\">"; }

            // Add the Booking details
            JLabel room = new JLabel("<html><body> " +
                    "<strong>" + nameGetter.getNameFromID(b.getPropertyID()) + "</strong>" +
                    "<br>Status: " + statusColour + b.getBookingStatus() + "</span>" +
                    "<br>Start Date: " + "<strong>" + formattedStartDate + "</strong>" +
                    "<br>End Date: " + "<strong>" + formattedEndDate + "</strong>" +
                    "<br>Total Cost: " + "<strong>£" + String.format("%.2f", b.getTotalPrice()) + "</strong>" +
                    "<br>Guest: " + "<strong>" + personNameGetter.getNameFromEmail(b.getEmail()) + "</strong>" +
                    "</body></html>");

            prop.add(room);
            prop.add(Box.createHorizontalGlue());
            prop.add(Box.createRigidArea(new Dimension(100, 10)));
            prop.add(Box.createHorizontalGlue());
            if (!(user.getIsGuest() && switchRoleFlag == 0)) {
                if(b.getBookingStatus().equalsIgnoreCase("Pending")) {
                    JButton acceptBtn = new JButton("Accept");
                    JButton rejectBtn = new JButton("Reject");
                    acceptBtn.setForeground(Color.BLUE);
                    acceptBtn.setFont(new Font(null,Font.BOLD,14));
                    rejectBtn.setForeground(Color.RED);
                    rejectBtn.setFont(new Font(null,Font.BOLD,14));
                    prop.add(acceptBtn);
                    prop.add(Box.createHorizontalGlue());
                    prop.add(rejectBtn);
                    prop.add(Box.createHorizontalGlue());

                    acceptBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String baseDateQuery = "SELECT * FROM bookings where bookingStatus = 'Accepted'" +
                                    "and startDateBooking <= ? and endDateBooking >= ? and propertyID = ?";
                            ResultSet dateResultSet;
                            try (Connection con = DriverManager.getConnection(
                                    "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
                                 PreparedStatement preparedDate = con.prepareStatement(baseDateQuery)) {

                                preparedDate.setDate(1, b.getEndDateBooking());
                                preparedDate.setDate(2, b.getEndDateBooking());
                                preparedDate.setInt(3, b.getPropertyID());
                                dateResultSet = preparedDate.executeQuery();
                                if (dateResultSet.next()){
                                    JOptionPane.showMessageDialog
                                            (null,
                                                    "Property is already booked during the given date window. \n" +
                                                            "Automatic rejection.",
                                                    "Sorry!",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                    b.reject();
                                    acceptBtn.setVisible(false);
                                    rejectBtn.setVisible(false);
                                    bookingPanelLoad();
                                }
                                else{
                                    b.accept();
                                    acceptBtn.setVisible(false);
                                    rejectBtn.setVisible(false);
                                    bookingPanelLoad();
                                }
                            } catch (SQLException a) {
                                throw new IllegalStateException("Connection Failed!", a);
                            } catch (Exception a) {
                                a.printStackTrace();
                            }

                        }
                    });
                    rejectBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            b.reject();
                            acceptBtn.setVisible(false);
                            rejectBtn.setVisible(false);
                            bookingPanelLoad();
                        }
                    });
                }
            }

            JButton reviewBtn = new JButton("More Details");
            reviewBtn.setFont(new Font(null,Font.BOLD,14));

            if (b.getBookingStatus().equalsIgnoreCase("Accepted")){
                JButton pDetailsBtn= new JButton("Private Details");
                pDetailsBtn.setFont(new Font(null,Font.BOLD,14));
                prop.add(pDetailsBtn);
                prop.add(Box.createHorizontalGlue());
                pDetailsBtn.addActionListener(e -> {
                    {
                        JFrame detailsFrame = new PrivateDetails(b, user, this);
                    }
                });
                if (user.getIsGuest() && switchRoleFlag == 0){
                    JButton reviewButton = new JButton("Write Review");
                    reviewButton.setFont(new Font(null,Font.BOLD,14));
                    prop.add(reviewButton);
                    prop.add(Box.createHorizontalGlue());
                    // Review frame
                    reviewButton.addActionListener(e -> {
                        {
                            JFrame reviewFrame = new WriteReview(b, user, this);
                        }
                    });
                }
            }
            // Add one result to the result list and set the UI format/
            result.add(prop);
            result.add(Box.createRigidArea(new Dimension(100, 10)));
        }

        return scrollPane;
    }

    /**
     * Add listeners to buttons
     */
    public void addListeners() {
        // Search Button
        searchBtn.addActionListener(e -> {
            String locationGot = cityName.getText();
            Date startGotVal = (Date) datePicker.getModel().getValue();
            Date endGotVal = (Date) datePicker2.getModel().getValue();
            String startGot;
            if (startGotVal==null){
                startGot = "Any";
            }
            else{
                startGot = datePicker.getModel().getValue().toString();
            }
            String endGot;
            if (endGotVal==null){
                endGot = "Any";
            }
            else{
                endGot = datePicker2.getModel().getValue().toString();
            }
            String guestsGot = guestNum.getSelectedItem().toString();
            if (startGot.equalsIgnoreCase("Any") && endGot.equalsIgnoreCase("Any")){
                resultList.removeAll();
                resultList.add(getResult(locationGot, startGot, endGot, guestsGot));
                resultList.revalidate();
            }
            else {
                if (startGot.equalsIgnoreCase("Any") || endGot.equalsIgnoreCase("Any") || (endGotVal.before(startGotVal))) {
                    JOptionPane.showMessageDialog
                            (null,
                                    "Please select valid dates!",
                                    "No Dates Selected!",
                                    JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    resultList.removeAll();
                    resultList.add(getResult(locationGot, startGot, endGot, guestsGot));
                    resultList.revalidate();
                }
            }
        });

        // Login Button
        logInBtn.addActionListener(e -> {
            JFrame loginFrame = new JFrame("Login");
            loginFrame.setContentPane(new Login(loginFrame, this).mainPanel);
            loginFrame.pack();
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);
        });

        // Register Button
        registerBtn.addActionListener(e -> {
            JFrame registerFrame = new JFrame("Register");
            registerFrame.setContentPane(new Register(registerFrame, this).mainPanel);
            registerFrame.pack();
            registerFrame.setLocationRelativeTo(null);
            registerFrame.setVisible(true);
        });

        // Booking stuff Button
        bookingBtn.addActionListener(e -> {
            mainListFlag = 1;
            searchListBtn.setVisible(true);
            bookingBtn.setVisible(false);
            searchPanel.setVisible(false);
            updateBookings();
            bookingPanelLoad();
        });

        // Show search list again Button
        searchListBtn.addActionListener(e -> {
            mainListFlag = 0;
            searchListBtn.setVisible(false);
            bookingBtn.setVisible(true);
            searchPanel.setVisible(true);
            bookingPanel.setVisible(false);
            // Update/refresh search results
            String locationGot = cityName.getText();
            String startGot;
            if (datePicker.getModel().getValue()==null){
                startGot = "Any";
            }
            else{
                startGot = datePicker.getModel().getValue().toString();
            }
            String endGot;
            if (datePicker2.getModel().getValue()==null){
                endGot = "Any";
            }
            else{
                endGot = datePicker2.getModel().getValue().toString();
            }
            String guestsGot = guestNum.getSelectedItem().toString();
            resultList.removeAll();
            resultList.add(getResult(locationGot, startGot, endGot, guestsGot));
            resultList.revalidate();
        });

        // Add property Button
        addPropBtn.addActionListener(e -> {
            new AddProperty(user, this);
            searchListBtn.doClick();
        });

       // Logout Button
        logOutBtn.addActionListener(e -> {
            Person emptyPerson = (new Person ("email",false, false));
            this.setUser(emptyPerson);
            JOptionPane.showOptionDialog(null,
                    "Thanks for using Homebreaks. You have successfully logged out.",
                    "Logged out", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, imgTransformed, null, null);
            searchListBtn.doClick();
            switchRoleFlag = 0;
            mainListFlag = 0;
            updateMenu();
        });

        switchRolesBtn.addActionListener(e -> {
            if (switchRoleFlag == 0) switchRoleFlag = 1; else switchRoleFlag = 0;
            updateMenu();
            updateBookings();
            if(mainListFlag==1){
                bookingPanelLoad();
            }
        });
    }

    /**
     * Update the menu panel buttons based on roles
     */
    public void updateMenu(){
        if (user.getIsGuest() && user.getIsHost()) {
            switchRolesBtn.setVisible(true);
        }
        if (mainListFlag == 0) {
            bookingBtn.setVisible(true);
        }

        if (user.getIsGuest() && switchRoleFlag == 0) {
            roleLabel.setText("Welcome, " + user.getEmail() + ", you are logged in as a Guest.");
            roleLabel.setFont(new Font(null,Font.BOLD,18));
            logInBtn.setVisible(false);
            registerBtn.setVisible(false);
            addPropBtn.setVisible(false);
            logOutBtn.setVisible(true);
        } else if (user.getIsHost()) {
            roleLabel.setText("Welcome, " + user.getEmail() + ", you are logged in as a Host.");
            roleLabel.setFont(new Font(null,Font.BOLD,18));
            logInBtn.setVisible(false);
            registerBtn.setVisible(false);
            addPropBtn.setVisible(true);
            logOutBtn.setVisible(true);
        } else {
            roleLabel.setText("Welcome, Enquirer!");
            roleLabel.setFont(new Font(null,Font.BOLD,18));
            switchRolesBtn.setVisible(false);
            logInBtn.setVisible(true);
            registerBtn.setVisible(true);
            bookingBtn.setVisible(false);
            addPropBtn.setVisible(false);
            logOutBtn.setVisible(false);
        }
    }

    /**
     * Update the bookings with review button depending on role
     */
    public void updateBookings() {
        if (user.getIsGuest() && switchRoleFlag == 0) {
            for (JButton btn : reviewButtons) {
                btn.setVisible(true);
            }
        } else {
            for (JButton btn : reviewButtons) {
                btn.setVisible(false);
            }
        }
    }

    /**
     * Load or refresh the bookings panel
     */
    public void bookingPanelLoad(){
        bookingPanel.setVisible(true);
        bookingPanel.removeAll();
        bookingPanel.add(getBooking());
        bookingPanel.revalidate();
    }
}
