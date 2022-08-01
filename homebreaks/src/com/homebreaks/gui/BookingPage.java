package com.homebreaks.gui;

import com.homebreaks.main.Booking;
import com.homebreaks.main.ChargeBand;
import com.homebreaks.main.Person;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

public class BookingPage extends JFrame {

    public BookingPage(Date startDate, Date endDate, int propertyId, Person user, JFrame detailsFrame){
        JFrame frame = new JFrame("Booking");
        JPanel mainPanel = new JPanel();
        JButton bookBtn = new JButton("Book Now!");
        JButton exitBtn = new JButton("Not This Time.");
        Font titleFont = new Font(null,Font.BOLD,20);
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        Border line = BorderFactory.createLineBorder(Color.BLACK,2);
        Border margin = BorderFactory.createEmptyBorder(10,10,10,10);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(line,margin));
        scrollPane.setBorder(margin);

        mainPanel.setLayout(new BorderLayout());
        JPanel receipt = new JPanel();
        receipt.setLayout(new BoxLayout(receipt,BoxLayout.Y_AXIS));

        Box receiptTitleB = Box.createHorizontalBox();
        JLabel receiptL = new JLabel("Price Breakdown");
        receiptL.setFont(titleFont);
        receiptTitleB.add(Box.createHorizontalGlue());
        receiptTitleB.add(receiptL);
        receiptTitleB.add(Box.createHorizontalGlue());
        receipt.add(receiptTitleB);
        ChargeBand costGetter = new ChargeBand();
        int numOfDays = (int) ChronoUnit.DAYS.between(startDate.toLocalDate(),endDate.toLocalDate());
        ChargeBand correctBand = costGetter.getCorrectBand(propertyId, startDate);
        double pricePerNight = correctBand.getPricePerNight();
        double serviceCharge = correctBand.getServiceCharge();
        double cleaningCharge = correctBand.getCleaningCharge();
        double totalPrice= ((numOfDays*pricePerNight) + serviceCharge + cleaningCharge);

        // Format the dates nicely
        LocalDate localStartDate = startDate.toLocalDate();
        LocalDate localEndDate = endDate.toLocalDate();
        String formattedStartDate = localStartDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        String formattedEndDate = localEndDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));

        Box chargeB = Box.createHorizontalBox();
        JLabel chargeL = new JLabel("<html><body style='width: 450'>"
                + "<br><strong>Start Date</strong>: " + formattedStartDate
                + "<br><strong>End Date</strong>: " + formattedEndDate
                + "<br><strong>Price per night</strong>: £" + String.format("%.2f", pricePerNight)
                + "<br><strong>Service charge</strong>: £" + String.format("%.2f", serviceCharge)
                + "<br><strong>Cleaning charge</strong>: £" + String.format("%.2f", cleaningCharge)
                + "<br><strong>Total</strong>: £" + String.format("%.2f", totalPrice)
                + "</body></html>");
        chargeL.setFont(new Font(null,Font.PLAIN,18));
        chargeB.add(chargeL);
        chargeB.add(Box.createHorizontalGlue());
        receipt.add(chargeB);

        Box btns = Box.createHorizontalBox();
        // Set the format of btns
        bookBtn.setFont(titleFont);
        bookBtn.setForeground(Color.BLUE);
        exitBtn.setFont(titleFont);
        exitBtn.setForeground(Color.RED);

        // Add btns to the Box
        btns.add(Box.createHorizontalGlue());
        bookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = user.getEmail();
                String baseDateQuery = "SELECT * FROM bookings where bookingStatus = 'Accepted'" +
                        "and startDateBooking <= ? and endDateBooking >= ? and propertyID = ?";

                ResultSet dateResultSet;
                try (Connection con = DriverManager.getConnection(
                        "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365");
                     PreparedStatement preparedDate = con.prepareStatement(baseDateQuery)) {

                    preparedDate.setDate(1, endDate);
                    preparedDate.setDate(2, startDate);
                    preparedDate.setInt(3, propertyId);
                    dateResultSet = preparedDate.executeQuery();
                    if (dateResultSet.next()){
                        JOptionPane.showMessageDialog
                                (null,
                                        "Property is already booked during the given date window.",
                                        "Sorry!",
                                        JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        Booking newBooking = new Booking(0, startDate, endDate,totalPrice,
                                "Pending",propertyId,email);
                        newBooking.addBooking();
                        JOptionPane.showMessageDialog
                                (null,
                                        "Your booking has been received. Please check the Bookings\n" +
                                                "page for updates. If the booking has been accepted, you'll\n" +
                                                "be able to see contact details for arranging your stay. Thank you!",
                                        "Booking received",
                                        JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (SQLException a) {
                    throw new IllegalStateException("Connection Failed!", a);
                } catch (Exception a) {
                    a.printStackTrace();
                }

                frame.dispose();
                detailsFrame.dispose();
            }
        });
        btns.add(bookBtn);
        btns.add(Box.createRigidArea(new Dimension(50,10)));
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        btns.add(exitBtn);
        btns.add(Box.createHorizontalGlue());

        // Add components to the panel
        mainPanel.add(receipt,BorderLayout.CENTER);
        mainPanel.add(btns,BorderLayout.SOUTH);

        frame.add(scrollPane);
        // Can set depends on the screen
        frame.setPreferredSize(new Dimension(500,300));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

}
