package com.homebreaks.gui;

import com.homebreaks.main.Booking;
import com.homebreaks.main.Person;
import com.homebreaks.main.Property;
import com.homebreaks.main.Review;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.HashMap;

public class WriteReview extends JFrame {
    private JFrame frame;
    private JPanel mainPanel;
    private JButton submitBtn = new JButton("Submit");
    private Font titleFont = new Font(null, Font.BOLD, 18);

    // Hashmap to store all the review scores
    private HashMap<String, Integer> reviewScores = new HashMap<>();

    // UI for the Review form
    public WriteReview(Booking booking, Person user, AbstractHomebreaksPanel panel) {
        frame = new JFrame("Writing a review");
        mainPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        Border line = BorderFactory.createLineBorder(Color.BLACK, 2);
        Border margin = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(line, margin));
        scrollPane.setBorder(margin);

        Box titleB = Box.createHorizontalBox();
        titleB.add(Box.createHorizontalGlue());
        JLabel titleL = new JLabel("Writing a Review");
        titleL.setFont(titleFont);
        titleB.add(titleL);
        titleB.add(Box.createHorizontalGlue());
        mainPanel.add(titleB);

        Box textTitleB = Box.createHorizontalBox();
        JLabel textTitleL = new JLabel("Review:");
        textTitleB.add(textTitleL);
        textTitleB.add(Box.createHorizontalGlue());
        mainPanel.add(textTitleB);

        Box textB = Box.createHorizontalBox();
        JTextArea textT = new JTextArea(3,8);
        textT.setBorder(line);
        textT.setLineWrap(true);
        textT.setMaximumSize(new Dimension(580,130));
        textT.setPreferredSize(new Dimension(580,130));
        textB.add(textT);
        textB.add(Box.createHorizontalGlue());
        mainPanel.add(textB);
        mainPanel.add(Box.createVerticalGlue());

        Box score1 = Box.createHorizontalBox();
        JLabel cleanScoreL = new JLabel("Cleanliness Score: ");

        JComboBox<Integer> cleanScoreC = new JComboBox<Integer>();
        for(int i=1;i<=5;i+=1)cleanScoreC.addItem(i);
        score1.add(cleanScoreL);
        score1.add(cleanScoreC);
        JLabel communicationScoreL = new JLabel("Communication Score: ");
        JComboBox<Integer> communicationScoreC = new JComboBox<Integer>();
        for(int i=1;i<=5;i+=1)communicationScoreC.addItem(i);
        score1.add(communicationScoreL);
        score1.add(communicationScoreC);
        JLabel checkInScoreL = new JLabel("CheckIn Score: ");
        JComboBox<Integer> checkInScoreC = new JComboBox<Integer>();
        for(int i=1;i<=5;i+=1)checkInScoreC.addItem(i);

        score1.add(checkInScoreL);
        score1.add(checkInScoreC);
        score1.add(Box.createHorizontalGlue());
        mainPanel.add(score1);
        Box score2 = Box.createHorizontalBox();
        JLabel accuracyScoreL = new JLabel("Accuracy Score: ");
        JComboBox<Integer> accuracyScoreC = new JComboBox<Integer>();
        for(int i=1;i<=5;i+=1)accuracyScoreC.addItem(i);
        score2.add(accuracyScoreL);
        score2.add(accuracyScoreC);
        JLabel locationScoreL = new JLabel("Location Score: ");
        JComboBox<Integer> locationScoreC = new JComboBox<Integer>();
        for(int i=1;i<=5;i+=1)locationScoreC.addItem(i);
        score2.add(locationScoreL);
        score2.add(locationScoreC);
        JLabel valueScoreL = new JLabel("Value Score: ");
        JComboBox<Integer> valueScoreC = new JComboBox<Integer>();
        for(int i=1;i<=5;i+=1)valueScoreC.addItem(i);

        score2.add(valueScoreL);
        score2.add(valueScoreC);
        score2.add(Box.createHorizontalGlue());
        mainPanel.add(score2);

        Box btnB = Box.createHorizontalBox();
        btnB.add(Box.createHorizontalGlue());
        submitBtn.setFont(titleFont);
        btnB.add(submitBtn);
        btnB.add(Box.createHorizontalGlue());
        mainPanel.add(btnB);
        mainPanel.add(Box.createVerticalGlue());

        // Submit Button. Calls function to add review to database
        submitBtn.addActionListener(e -> {
            Review userReview = new Review(booking, user);
            reviewScores.put("cleanliness", Integer.parseInt(cleanScoreC.getSelectedItem().toString()));
            reviewScores.put("communication", Integer.parseInt(communicationScoreC.getSelectedItem().toString()));
            reviewScores.put("checkin", Integer.parseInt(checkInScoreC.getSelectedItem().toString()));
            reviewScores.put("accuracy", Integer.parseInt(accuracyScoreC.getSelectedItem().toString()));
            reviewScores.put("location", Integer.parseInt(locationScoreC.getSelectedItem().toString()));
            reviewScores.put("value", Integer.parseInt(valueScoreC.getSelectedItem().toString()));
            userReview.createReview(textT.getText(), reviewScores);
            frame.dispose();
            panel.updateMenu();
            JOptionPane.showOptionDialog(null,
                    "Thank you for submitting your review. We hope you enjoyed your stay.",
                    "Review submitted", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, panel.imgTransformed, null, null);
        });

        frame.add(scrollPane);
        frame.setPreferredSize(new Dimension(630,350));
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the form
        frame.setVisible(true);
    }
}
