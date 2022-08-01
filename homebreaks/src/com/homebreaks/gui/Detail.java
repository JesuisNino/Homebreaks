package com.homebreaks.gui;

import com.homebreaks.main.*;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Detail extends JFrame {

    private Property property;
    private int propertyID;
    private List<Review> reviews;
    private double cleanliness;
    private double communication;
    private double checkin;
    private double accuracy;
    private double location;
    private double value;
    private double averageReview;
    private JLabel scoreTitleL = new JLabel();
    private JLabel cleanScore = new JLabel();
    private JLabel communicationScore = new JLabel();
    private JLabel checkInScore = new JLabel();
    private JLabel accuracyScore = new JLabel();
    private JLabel locationScore = new JLabel();
    private JLabel valueScore = new JLabel();
    private final String NO_REVIEWS = "No reviews yet - be the first!";
    private final String NOT_APPLICABLE = "n/a";
    private String superhost;
    private Host host = new Host();

    public Detail(Property property, Person user, int switchRoleFlag) {
        this.property = property;
        this.propertyID = property.getPropertyID();
        this.reviews = getReviews(propertyID);
        this.host = getHost(property.getEmail());

        // total review scores by category
        int counter = 0;
        for (Review review: reviews) {
            cleanliness += review.getCleanliness();
            communication += review.getCommunication();
            checkin += review.getCheckin();
            accuracy += review.getAccuracy();
            location += review.getLocation();
            value += review.getValue();
            counter += 1;
        }

        // mean review scores
        cleanliness /= counter;
        communication /= counter;
        checkin /= counter;
        accuracy /= counter;
        location /= counter;
        value /= counter;
        averageReview = (cleanliness + communication + checkin + accuracy + location + value) / 6;

        // Handle NaN cases for when a Property has no associated reviews
         if (Double.isNaN(averageReview)) {
            scoreTitleL.setText(NO_REVIEWS);
             cleanScore.setText("Cleanliness: " + NOT_APPLICABLE);
             communicationScore.setText("Communication: "+ NOT_APPLICABLE);
             checkInScore.setText("Check-in: "+ NOT_APPLICABLE);
             accuracyScore.setText("Accuracy: " + NOT_APPLICABLE);
             locationScore.setText("Location: " + NOT_APPLICABLE);
             valueScore.setText("Value: " + NOT_APPLICABLE);
        } else {
            scoreTitleL.setText("Average Score: " + String.format("%.1f", averageReview));
            cleanScore.setText("Cleanliness: " + String.format("%.1f", cleanliness));
            communicationScore.setText("Communication: " + String.format("%.1f", communication));
            checkInScore.setText("Check-in: " + String.format("%.1f", checkin));
            accuracyScore.setText("Accuracy: " + String.format("%.1f", accuracy));
            locationScore.setText("Location: " + String.format("%.1f", location));
            valueScore.setText("Value: " + String.format("%.1f", value));
        }

        // Add the bathrooms and bedrooms
        property.getSleeping().setBedrooms(getBedroomsFromID(propertyID));
        property.getBathing().setBathrooms(getBathroomsFromID(propertyID));

        // UI Stuff
        JFrame frame = new JFrame("Detail");
        JPanel mainPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        Font titleFont = new Font(null,Font.BOLD,16);
        Font detailFont = new Font(null,Font.PLAIN,14);
        Border lineBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
        Border margin = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        SqlDateModel dateModelStart = new SqlDateModel();
        SqlDateModel dateModelEnd = new SqlDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModelStart);
        JDatePanelImpl datePanel2 = new JDatePanelImpl(dateModelEnd);
        JDatePickerImpl datePickerStart = new JDatePickerImpl(datePanel);
        JDatePickerImpl datePickerEnd = new JDatePickerImpl(datePanel2);

        // Create the format of this page
        mainPanel.setLayout(new BorderLayout());
        Box head = Box.createHorizontalBox();
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));

        // Add header
        head.add(Box.createHorizontalGlue());
        head.add(Box.createRigidArea(new Dimension(5,10)));

        if ((user.getIsGuest()) && switchRoleFlag == 0) {
            head.add(new JLabel("Start Date"));
            head.add(datePickerStart);
            head.add(new JLabel("End Date"));
            head.add(datePickerEnd);
            JButton bookBtn = new JButton("Book Now!");
            bookBtn.setFont(titleFont);
            head.add(bookBtn);
            head.add(Box.createHorizontalGlue());

            bookBtn.addActionListener(e -> {
                {
                    Date startGot = (Date) datePickerStart.getModel().getValue();
                    Date endGot = (Date) datePickerEnd.getModel().getValue();
                    if (startGot == null || endGot == null || (endGot.before(startGot))) {
                        JOptionPane.showMessageDialog
                                (null,
                                        "Please select valid dates!",
                                        "No Dates Selected!",
                                        JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        if((user.getIsGuest()&&user.getIsHost())&&(property.getEmail().equalsIgnoreCase(user.getEmail()))){
                            JOptionPane.showMessageDialog
                                    (null,
                                            "Please do not try to make a booking at your own property!",
                                            "You own this place!",
                                            JOptionPane.INFORMATION_MESSAGE);
                        }
                        else {
                            new BookingPage(startGot, endGot, propertyID, user, frame);
                        }
                    }
                }
            });
        }

        // Get the current property information
        JLabel nameL = new JLabel(property.getName());
        nameL.setFont(new Font(null,Font.BOLD,20));
        Box nameB = Box.createHorizontalBox();
        nameB.add(nameL);
        nameB.add(Box.createHorizontalGlue());
        main.add(nameB);
        main.add(Box.createRigidArea(new Dimension(0,5)));

        Box text = Box.createVerticalBox();
        Box hostNameB = Box.createHorizontalBox();
        JLabel hostName = new JLabel("Hosted by " + host.getPublicName());
        JLabel tips = new JLabel("Tip: Superhost has a highlight red name!");
        // Work out if superhost or not (if NaN, will return false so no need to explicitly handle this)
        if (host.calculateAverageReview() >= 4.7) {
            hostName.setForeground(Color.RED);
            hostName.setFont(new Font(null,Font.BOLD,20));
        } else {
            hostName.setForeground(Color.BLACK);
            hostName.setFont(new Font(null,Font.PLAIN,16));
        }
        hostNameB.add(hostName);
        hostNameB.add(Box.createHorizontalGlue());
        hostNameB.add(tips);
        text.add(hostNameB);

        Box basicB = Box.createHorizontalBox();
        String breakfast;
        if(property.getHasBreakfast()==1){
            breakfast = "Yes";
        }else{
            breakfast = "No";
        }
        JLabel basicL = new JLabel(
                "<html><body style='width:750'>"
                + "<strong>" + "Location: " + "</strong>" + property.getLocation() +"<br><br>"
                + "<strong>" + "Description: " + "</strong>" + property.getDescription() + "<br><br>"
                + "<strong>" + "Breakfast Included: " + "<strong>" + breakfast
                +"</body></html>"
        );
        basicL.setFont(detailFont);
        basicB.add(basicL);
        basicB.add(Box.createHorizontalGlue());
        text.add(basicB);
        text.setBorder(BorderFactory.createCompoundBorder(lineBorder, margin));
        main.add(text);
        main.add(Box.createRigidArea(new Dimension(0,10)));

        Box detailTitleB = Box.createHorizontalBox();
        JLabel detailTitleL = new JLabel("Facilities:");
        detailTitleL.setFont(titleFont);
        detailTitleB.add(detailTitleL);
        detailTitleB.add(Box.createHorizontalGlue());
        main.add(detailTitleB);

        Box btnsB = Box.createHorizontalBox();

        Box sleepingB = Box.createHorizontalBox();
        sleepingB.setBorder(BorderFactory.createCompoundBorder(lineBorder, margin));
        JLabel sleepingL = new JLabel("<html><body>"
                                           +"Number of bedrooms: " + property.getSleeping().getBedroomCount()+"<br>"
                                           +"Number of beds: " + property.getSleeping().getBedCount()+"<br>"
                                           +"Linen provided: " + yesNoHelper(property.getSleeping().getHasLinen())+"<br>"
                                           +"Towels provided: " + yesNoHelper(property.getSleeping().getHasTowel())
                                           +"</body></html>");
        sleepingB.add(sleepingL);
        Box bathingB = Box.createHorizontalBox();
        bathingB.setBorder(BorderFactory.createCompoundBorder(lineBorder, margin));
        JLabel bathingL = new JLabel("<html><body>"
                +"Number of bathrooms: " + property.getBathing().getBathroomCount()+"<br>"
                +"Hair drier provided: " + yesNoHelper(property.getBathing().getHasHairDryer())+"<br>"
                +"Shampoo provided: " + yesNoHelper(property.getBathing().getHasShampoo())+"<br>"
                +"Toilet paper provided: " + yesNoHelper(property.getBathing().getHasToiletPaper())
                +"</body></html>");
        bathingB.add(bathingL);
        Box kitchenB = Box.createHorizontalBox();
        kitchenB.setBorder(BorderFactory.createCompoundBorder(lineBorder, margin));
        JLabel kitchenL = new JLabel("<html><body>"
                +"Fridge: "+yesNoHelper(property.getKitchen().getHasFridge())+"<br>"
                +"Microwave: "+yesNoHelper(property.getKitchen().getHasMicro())+"<br>"
                +"Oven: "+yesNoHelper(property.getKitchen().getHasOven())+"<br>"
                +"Stove: "+yesNoHelper(property.getKitchen().getHasStove())+"<br>"
                +"Dishwasher: "+yesNoHelper(property.getKitchen().getHasDishwasher())+"<br>"
                +"Tableware: "+yesNoHelper(property.getKitchen().getHasTableware())+"<br>"
                +"Cookware: "+yesNoHelper(property.getKitchen().getHasCookware())+"<br>"
                +"Basics: "+yesNoHelper(property.getKitchen().getHasBasics())
                +"</body></html>");
        kitchenB.add(kitchenL);
        Box livingB = Box.createHorizontalBox();
        livingB.setBorder(BorderFactory.createCompoundBorder(lineBorder, margin));
        JLabel livingL = new JLabel("<html><body>"
                +"WiFi: "+yesNoHelper(property.getLiving().getHasWLAN())+"<br>"
                +"TV: "+yesNoHelper(property.getLiving().getHasTV())+"<br>"
                +"Satellite: "+yesNoHelper(property.getLiving().getHasSatellite())+"<br>"
                +"Streaming: "+yesNoHelper(property.getLiving().getHasStreaming())+"<br>"
                +"DVD player: "+yesNoHelper(property.getLiving().getHasDVD())+"<br>"
                +"Board games: "+yesNoHelper(property.getLiving().getHasBoardGames())
                +"</body></html>");
        livingB.add(livingL);
        Box utilityB = Box.createHorizontalBox();
        utilityB.setBorder(BorderFactory.createCompoundBorder(lineBorder, margin));
        JLabel utilityL = new JLabel("<html><body>"
                +"Central heating: "+yesNoHelper(property.getUtility().getHasHeating())+"<br>"
                +"Washing machine: "+yesNoHelper(property.getUtility().getHasWasher())+"<br>"
                +"Tumble drier: "+yesNoHelper(property.getUtility().getHasClothesDryer())+"<br>"
                +"Fire extinguisher: "+yesNoHelper(property.getUtility().getHasExtinguisher())+"<br>"
                +"Smoke alarm: "+yesNoHelper(property.getUtility().getHasSmokeAlarm())+"<br>"
                +"First aid kit: "+yesNoHelper(property.getUtility().getHasFirstAid())
                +"</body></html>");
        utilityB.add(utilityL);
        Box outdoorsB = Box.createHorizontalBox();
        outdoorsB.setBorder(BorderFactory.createCompoundBorder(lineBorder, margin));
        JLabel outdoorsL = new JLabel("<html><body>"
                +"On-site parking: "+yesNoHelper(property.getOutdoors().getHasOnSiteParking())+"<br>"
                +"On-road parking: "+yesNoHelper(property.getOutdoors().getHasRoadParking())+"<br>"
                +"Paid car park: "+yesNoHelper(property.getOutdoors().getHasCarPark())+"<br>"
                +"Patio: "+yesNoHelper(property.getOutdoors().getHasPatio())+"<br>"
                +"Barbeque: "+yesNoHelper(property.getOutdoors().getHasBBQ())
                +"</body></html>");
        outdoorsB.add(outdoorsL);

        JButton sleepingBtn = new JButton("Sleeping");
        sleepingBtn.setFont(titleFont);
        sleepingB.setVisible(true);
        sleepingBtn.addActionListener(e -> {
            sleepingB.setVisible(true);
            bathingB.setVisible(false);
            kitchenB.setVisible(false);
            livingB.setVisible(false);
            utilityB.setVisible(false);
            outdoorsB.setVisible(false);
        });
        btnsB.add(sleepingBtn);
        JButton bathingBtn = new JButton("Bathing");
        bathingBtn.setFont(titleFont);
        bathingB.setVisible(false);
        bathingBtn.addActionListener(e -> {
            sleepingB.setVisible(false);
            bathingB.setVisible(true);
            kitchenB.setVisible(false);
            livingB.setVisible(false);
            utilityB.setVisible(false);
            outdoorsB.setVisible(false);
        });
        btnsB.add(bathingBtn);
        JButton kitchenBtn = new JButton("Kitchen");
        kitchenBtn.setFont(titleFont);
        kitchenB.setVisible(false);
        kitchenBtn.addActionListener(e -> {
            sleepingB.setVisible(false);
            bathingB.setVisible(false);
            kitchenB.setVisible(true);
            livingB.setVisible(false);
            utilityB.setVisible(false);
            outdoorsB.setVisible(false);
        });
        btnsB.add(kitchenBtn);
        JButton livingBtn = new JButton("Living");
        livingBtn.setFont(titleFont);
        livingB.setVisible(false);
        livingBtn.addActionListener(e -> {
            sleepingB.setVisible(false);
            bathingB.setVisible(false);
            kitchenB.setVisible(false);
            livingB.setVisible(true);
            utilityB.setVisible(false);
            outdoorsB.setVisible(false);
        });
        btnsB.add(livingBtn);
        JButton utilityBtn = new JButton("Utility");
        utilityBtn.setFont(titleFont);
        utilityB.setVisible(false);
        utilityBtn.addActionListener(e -> {
            sleepingB.setVisible(false);
            bathingB.setVisible(false);
            kitchenB.setVisible(false);
            livingB.setVisible(false);
            utilityB.setVisible(true);
            outdoorsB.setVisible(false);
        });
        btnsB.add(utilityBtn);
        JButton outdoorsBtn = new JButton("Outdoors");
        outdoorsBtn.setFont(titleFont);
        outdoorsB.setVisible(false);
        outdoorsBtn.addActionListener(e -> {
            sleepingB.setVisible(false);
            bathingB.setVisible(false);
            kitchenB.setVisible(false);
            livingB.setVisible(false);
            utilityB.setVisible(false);
            outdoorsB.setVisible(true);
        });
        btnsB.add(outdoorsBtn);
        btnsB.add(Box.createHorizontalGlue());

        main.add(btnsB);

        main.add(sleepingB);
        main.add(bathingB);
        main.add(kitchenB);
        main.add(livingB);
        main.add(utilityB);
        main.add(outdoorsB);
        main.add(Box.createRigidArea(new Dimension(0,10)));

        Box scoreTitleB = Box.createHorizontalBox();
        scoreTitleL.setFont(titleFont);
        scoreTitleB.add(scoreTitleL);
        scoreTitleB.add(Box.createHorizontalGlue());
        main.add(scoreTitleB);

        Box scores = Box.createVerticalBox();
        Box score1 = Box.createHorizontalBox();
        score1.add(cleanScore);
        score1.add(Box.createHorizontalGlue());
        score1.add(communicationScore);
        score1.add(Box.createHorizontalGlue());
        score1.add(checkInScore);
        score1.add(Box.createHorizontalGlue());
        scores.add(score1);

        Box score2 = Box.createHorizontalBox();
        score2.add(accuracyScore);
        score2.add(Box.createHorizontalGlue());
        score2.add(locationScore);
        score2.add(Box.createHorizontalGlue());
        score2.add(valueScore);
        score2.add(Box.createHorizontalGlue());
        scores.add(score2);

        score1.setFont(detailFont);
        score2.setFont(detailFont);
        scores.setBorder(BorderFactory.createCompoundBorder(lineBorder, margin));

        main.add(scores);
        main.add(Box.createRigidArea(new Dimension(0,10)));

        Box reviewTitleB = Box.createHorizontalBox();
        if (reviews.size() != 0) {
            JLabel reviewTitleL = new JLabel("Latest reviews");
            reviewTitleL.setFont(titleFont);
            reviewTitleB.add(reviewTitleL);
            reviewTitleB.add(Box.createHorizontalGlue());
            main.add(reviewTitleB);
        }

        // Reverse the list so that the newest reviews are displayed first
        Collections.reverse(reviews);
        for(Review review: reviews){
            Box reviewB = Box.createHorizontalBox();
            // give name a value
            JLabel scoreL = new JLabel("[" + String.format("%.1f", review.getAverageScore()) + "] ");
            Font scoreFont = new Font("Courier", Font.BOLD,12);
            scoreL.setFont(scoreFont);

            // Add a short description to the Review on the Detail page
            String shortDescriptionString = review.getDescription();
            if (shortDescriptionString.length() > 90) {
                shortDescriptionString = shortDescriptionString.substring(0, 90) + "...";
            }
            JLabel shortDescription = new JLabel(shortDescriptionString);
            // add the score after "Average Score"
            reviewB.add(scoreL);
            reviewB.add(shortDescription);
            reviewB.add(Box.createHorizontalGlue());
            JButton viewB = new JButton("View more");
            viewB.addActionListener(e -> {
                new ViewReview(review);
            });
            reviewB.add(viewB);
            reviewB.setBorder(BorderFactory.createCompoundBorder(lineBorder, margin));
            main.add(reviewB);
        }
        main.add(Box.createVerticalGlue());

        Border mainBorder = BorderFactory.createLineBorder(Color.GRAY,2);
        main.setBorder(BorderFactory.createCompoundBorder(mainBorder,margin));

        // Add components to the panel
        mainPanel.add(head,BorderLayout.NORTH);
        mainPanel.add(main,BorderLayout.CENTER);
        frame.add(scrollPane);

        // Can set depends on the screen
        frame.setPreferredSize(new Dimension(850,680));
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the form
        frame.setVisible(true);
    }

    /**
     * Gets list of bookings for single property belonging to host.
     *
     * @param propertyID ID of property
     * @return list of reviews associated with property
     */
    public static List<Review> getReviews(int propertyID) {
        String reviewQuery = "SELECT * FROM reviews WHERE propertyID = '" + propertyID + "';";
        List<Review> reviews = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
             PreparedStatement ps = con.prepareStatement(reviewQuery)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int reviewID = rs.getInt("reviewID");
                String description = rs.getString("description");
                int cleanliness = rs.getInt("cleanliness");
                int communication = rs.getInt("communication");
                int checkin = rs.getInt("checkin");
                int accuracy = rs.getInt("accuracy");
                int location = rs.getInt("location");
                int value = rs.getInt("value");
                String email = rs.getString("email");

                Review review = new Review(reviewID, description, cleanliness, communication, checkin, accuracy, location,
                        value, propertyID, email);

                reviews.add(review);
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Connection Failed!", e);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reviews;
    }

    /**
     * Gets Host from email address
     *
     * @param email email address of host
     * @return host object of host
     */
    public Host getHost(String email) {
        String query = "SELECT * FROM hosts WHERE email = ?";
        try (Connection con = DriverManager.getConnection
                ("jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365")) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, property.getEmail());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                host.setPublicName(rs.getString("publicName"));
                if (rs.getInt("isSuperHost") == 0) host.setIsSuper(false); else host.setIsSuper(true);
                host.setEmail(rs.getString("email"));
                }


            } catch (SQLException e) {
            e.printStackTrace();
        }

        return host;
    }

    /**
     * Gets Host from email address
     *
     * @param propertyID ID of property
     * @return list of bedrooms for a given property
     */
    public static List<Bedroom> getBedroomsFromID(int propertyID) {
        String bedroomQuery = "SELECT * FROM bedrooms WHERE propertyID = '" + propertyID + "';";
        List<Bedroom> bedrooms = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
             PreparedStatement ps = con.prepareStatement(bedroomQuery)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int bedOne = rs.getInt("bedOne");
                int bedTwo = rs.getInt("bedTwo");
                Bedroom bedroom = new Bedroom(bedOne, bedTwo);
                bedrooms.add(bedroom);
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Connection Failed!", e);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bedrooms;
    }

    /**
     * Gets Host from email address
     *
     * @param propertyID ID of property
     * @return list of bathrooms for a given property
     */
    public static List<Bathroom> getBathroomsFromID(int propertyID) {
        String bathroomQuery = "SELECT * FROM bathrooms WHERE propertyID = '" + propertyID + "';";
        List<Bathroom> bathrooms = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
             PreparedStatement ps = con.prepareStatement(bathroomQuery)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int hasToilet = rs.getInt("hasToilet");
                int hasBath = rs.getInt("hasBath");
                int hasShower = rs.getInt("hasShower");
                int isShared = rs.getInt("isShared");
                Bathroom bathroom = new Bathroom(hasToilet, hasBath, hasShower, isShared);
                bathrooms.add(bathroom);
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Connection Failed!", e);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bathrooms;
    }

    /**
     * Helper function to display 'yes' or 'no' string for pretty display of boolean values
     *
     * @param value 0 or 1 value representing Boolean from database
     * @return string with either "yes" or "no"
     */
    public String yesNoHelper(int value) {
        if (value == 0) return "No"; else {
            return "Yes";
        }

    }

}
