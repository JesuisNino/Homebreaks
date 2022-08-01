package com.homebreaks.gui;

import com.homebreaks.main.Person;
import com.homebreaks.main.Property;
import com.homebreaks.main.Review;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.*;

public class ViewReview extends JFrame {
    private JFrame frame;
    private JPanel mainPanel;
    private Font titleFont = new Font(null, Font.BOLD, 18);
    private JLabel nameL = new JLabel();

    public ViewReview(Review review) {
        frame = new JFrame("Review");
        mainPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        Border line = BorderFactory.createLineBorder(Color.BLACK, 2);
        Border margin = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(line, margin));
        scrollPane.setBorder(margin);

        Box titleB = Box.createHorizontalBox();
        titleB.add(Box.createHorizontalGlue());
        JLabel titleL = new JLabel("Review");
        titleL.setFont(titleFont);
        titleB.add(titleL);
        titleB.add(Box.createHorizontalGlue());
        mainPanel.add(titleB);

        // Get the guest name
        String email = review.getEmail();
        String guestName = getGuestName(email);

        Box nameB = Box.createHorizontalBox();
        nameL.setText(guestName);
        nameL.setFont(titleFont);
        nameB.add(nameL);
        nameB.add(Box.createHorizontalGlue());
        mainPanel.add(nameB);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));

        Box textB = Box.createHorizontalBox();
        JLabel textL = new JLabel();
        textL.setSize(300,0);
        textL.setText("<html><body style='width: 460'>"+review.getDescription()+"</body></html>");
        textB.add(textL);
        textB.add(Box.createHorizontalGlue());
        mainPanel.add(textB);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));

        Box score1 = Box.createHorizontalBox();
        JLabel cleanScoreL = new JLabel("<html><body><strong>" + "Cleanliness Score: " + "</strong>"
                + review.getCleanliness() + "</body></html>");
        score1.add(cleanScoreL);
        score1.add(Box.createHorizontalGlue());
        JLabel communicationScoreL = new JLabel( "<html><body><strong>" + "Communication Score: " + "</strong>"
                + review.getCommunication() + "</body></html>");
        score1.add(communicationScoreL);
        score1.add(Box.createHorizontalGlue());
        mainPanel.add(score1);

        Box score2 = Box.createHorizontalBox();
        JLabel checkInScoreL = new JLabel( "<html><body><strong>" + "CheckIn Score: " + "</strong>"
                + review.getCheckin() + "</body></html>");
        score2.add(checkInScoreL);
        score2.add(Box.createHorizontalGlue());
        JLabel accuracyScoreL = new JLabel("<html><body><strong>" + "Accuracy Score: " + "</strong>"
                + review.getAccuracy() + "</body></html>");
        score2.add(accuracyScoreL);
        score2.add(Box.createHorizontalGlue());
        mainPanel.add(score2);

        Box score3 = Box.createHorizontalBox();
        JLabel locationScoreL = new JLabel("<html><body><strong>" + "Location Score: " + "</strong>"
                + review.getLocation() + "</body></html>");
        score3.add(locationScoreL);
        score3.add(Box.createHorizontalGlue());
        JLabel valueScoreL = new JLabel("<html><body><strong>" + "Value Score: " + "</strong>"
                + review.getValue() + "</body></html>");
        score3.add(valueScoreL);
        score3.add(Box.createHorizontalGlue());
        mainPanel.add(score3);
        mainPanel.add(Box.createVerticalGlue());

        frame.add(scrollPane);
        frame.setPreferredSize(new Dimension(550,350));
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the form
        frame.setVisible(true);
    }

    /**
     * Get search results based on user parameters
     *
     * @param email email of person
     * @return public name of guest
     */
    public String getGuestName(String email) {
        String publicName = "";
        try (Connection conn = DriverManager.getConnection
                ("jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365")) {
            String query = "SELECT publicName FROM guests WHERE email = '" + email + "';";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                publicName = rs.getString("publicName");
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Connection Failed!", e);
        }

        return publicName;
    }

}
