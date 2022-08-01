package com.homebreaks.gui;

import com.homebreaks.main.*;
import com.homebreaks.util.FieldValidator;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.Date;

public class AddProperty extends JFrame {
    private int propertyID;
    private Person user;
    private Property prop = new Property();
    private final AbstractHomebreaksPanel PANEL;
    private List<ChargeBand> chargeBands = new ArrayList<>();
    private List<JTextField> pricesPerNight = new ArrayList<>();
    private List<JTextField> cleaningCharges = new ArrayList<>();
    private List<JTextField> serviceCharges = new ArrayList<>();

    int index = 0;
    List<JDatePickerImpl> datePickerEndList = new ArrayList<>();
    List<JDatePanelImpl> datePanelEndList = new ArrayList<>();
    List<JLabel> startLabelList = new ArrayList<>();
    List<JLabel> endLabelList = new ArrayList<>();
    Date firstDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
    Date lastDate = Date.valueOf("2022-12-31");;

    public AddProperty(Person user, AbstractHomebreaksPanel panel) {
        this.user = user;
        this.PANEL = panel;
        JFrame frame = new JFrame("Add Property");
        JPanel mainPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(800, 400)); // Can set depending on the screen
        Border line = BorderFactory.createLineBorder(Color.BLACK,2);
        Border margin = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border chargeLine = BorderFactory.createLineBorder(Color.BLACK,1);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(line,margin));
        scrollPane.setBorder(margin);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        Box titleB = Box.createHorizontalBox();
        JLabel titleL = new JLabel("Add a property");
        titleL.setFont(new Font(null, Font.BOLD, 16));
        titleB.add(titleL);
        mainPanel.add(titleB);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        Box nameB = Box.createHorizontalBox();
        JLabel nameL = new JLabel("Name");
        nameB.add(nameL);
        JTextField nameT = new JTextField();
        nameT.setPreferredSize(new Dimension(570,20));
        nameT.setMaximumSize(new Dimension(570,20));
        nameB.add(nameT);
        nameB.add(Box.createHorizontalGlue());
        mainPanel.add(nameB);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        Box descriptionB = Box.createHorizontalBox();
        JLabel descriptionL = new JLabel("Description");
        descriptionB.add(descriptionL);
        JTextField descriptionT = new JTextField();
        descriptionT.setPreferredSize(new Dimension(530,20));
        descriptionT.setMaximumSize(new Dimension(530,20));
        descriptionB.add(descriptionT);
        descriptionB.add(Box.createHorizontalGlue());
        mainPanel.add(descriptionB);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        Box locationB = Box.createHorizontalBox();
        JLabel locationL = new JLabel("Searchable Location");
        locationB.add(locationL);
        JTextField locationT = new JTextField();
        locationT.setPreferredSize(new Dimension(480,20));
        locationT.setMaximumSize(new Dimension(480,20));
        locationB.add(locationT);
        locationB.add(Box.createHorizontalGlue());
        mainPanel.add(locationB);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        Box addOneB = Box.createHorizontalBox();
        JLabel addOneL = new JLabel("Address Line 1");
        addOneB.add(addOneL);
        JTextField addOneT = new JTextField();
        addOneT.setPreferredSize(new Dimension(510,20));
        addOneT.setMaximumSize(new Dimension(510,20));
        addOneB.add(addOneT);
        addOneB.add(Box.createHorizontalGlue());
        mainPanel.add(addOneB);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        Box addTwoB = Box.createHorizontalBox();
        JLabel addTwoL = new JLabel("Address Line 2");
        addTwoB.add(addTwoL);
        JTextField addTwoT = new JTextField();
        addTwoT.setPreferredSize(new Dimension(510,20));
        addTwoT.setMaximumSize(new Dimension(510,20));
        addTwoB.add(addTwoT);
        addTwoB.add(Box.createHorizontalGlue());
        mainPanel.add(addTwoB);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        Box cityB = Box.createHorizontalBox();
        JLabel cityL = new JLabel("City");
        cityB.add(cityL);
        JTextField cityT = new JTextField();
        cityT.setPreferredSize(new Dimension(580,20));
        cityT.setMaximumSize(new Dimension(580,20));
        cityB.add(cityT);
        cityB.add(Box.createHorizontalGlue());
        mainPanel.add(cityB);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        Box postcodeB = Box.createHorizontalBox();
        JLabel postcodeL = new JLabel("Post Code");
        postcodeB.add(postcodeL);
        JTextField postcodeT = new JTextField();
        postcodeT.setPreferredSize(new Dimension(540,20));
        postcodeT.setMaximumSize(new Dimension(540,20));
        postcodeB.add(postcodeT);
        postcodeB.add(Box.createHorizontalGlue());
        mainPanel.add(postcodeB);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Chargebands - maximum of 4 chargebands per property listing
        // It could have been an arbitrary number but we decided to limit this
        List<Box> charges = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            SqlDateModel dateModelEnd = new SqlDateModel();
            JDatePanelImpl datePanelEnd = new JDatePanelImpl(dateModelEnd);
            JDatePickerImpl datePickerEnd = new JDatePickerImpl(datePanelEnd);
            Box chargeB = Box.createVerticalBox();
            Box charge1 = Box.createHorizontalBox();
            Box charge2 = Box.createHorizontalBox();
            chargeB.add(charge1);
            chargeB.add(Box.createRigidArea(new Dimension(0,5)));
            chargeB.add(charge2);

            JLabel startDateL = new JLabel("Available Start Date: ");
            charge1.add(startDateL);
            JLabel startDateLabel;
            if(i<1) {
                startDateLabel = new JLabel(firstDate.toString());
            }
            else{
                startDateLabel = new JLabel();
            }
            startDateLabel.setPreferredSize(new Dimension(150,30));
            startDateLabel.setMaximumSize(new Dimension(150,30));
            charge1.add(startDateLabel);
            charge1.add(Box.createRigidArea(new Dimension(10,0)));
            JLabel endDateL = new JLabel("Available End Date: ");
            charge1.add(endDateL);
            datePickerEnd.setPreferredSize(new Dimension(150,30));
            datePickerEnd.setMaximumSize(new Dimension(150,30));
            charge1.add(datePickerEnd);
            JLabel endDateLabel = new JLabel();
            endDateLabel.setPreferredSize(new Dimension(150,30));
            endDateLabel.setMaximumSize(new Dimension(150,30));
            charge1.add(endDateLabel);
            charge1.add(Box.createHorizontalGlue());

            JLabel pricePerNightL = new JLabel("Price Per Night");
            charge2.add(pricePerNightL);
            JFormattedTextField pricePerNightT = new JFormattedTextField(NumberFormat.getNumberInstance());
            pricePerNightT.setPreferredSize(new Dimension(70,20));
            pricePerNightT.setMaximumSize(new Dimension(70,20));
            charge2.add(pricePerNightT);
            charge2.add(Box.createRigidArea(new Dimension(10,0)));

            JLabel serviceChargeL = new JLabel("Service Charge");
            charge2.add(serviceChargeL);
            JFormattedTextField serviceChargeT = new JFormattedTextField(NumberFormat.getNumberInstance());
            serviceChargeT.setPreferredSize(new Dimension(70,20));
            serviceChargeT.setMaximumSize(new Dimension(70,20));
            charge2.add(serviceChargeT);
            charge2.add(Box.createRigidArea(new Dimension(10,0)));

            JLabel cleanChargeL = new JLabel("Cleaning Charge");
            charge2.add(cleanChargeL);
            JFormattedTextField cleanChargeT = new JFormattedTextField(NumberFormat.getNumberInstance());
            cleanChargeT.setPreferredSize(new Dimension(70,20));
            cleanChargeT.setMaximumSize(new Dimension(70,20));
            charge2.add(cleanChargeT);
            charge2.add(Box.createHorizontalGlue());

            mainPanel.add(chargeB);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            chargeB.setVisible(false);
            charges.add(chargeB);

            // Put the textfields in an array to access the values for Chargeband creation
            pricesPerNight.add(pricePerNightT);
            serviceCharges.add(serviceChargeT);
            cleaningCharges.add(cleanChargeT);
            datePanelEndList.add(datePanelEnd);
            datePickerEndList.add(datePickerEnd);
            startLabelList.add(startDateLabel);
            endLabelList.add(endDateLabel);

            charges.get(i).setPreferredSize(new Dimension(400, 90));
            charges.get(i).setBorder(BorderFactory.createCompoundBorder(chargeLine,margin));
        }

        charges.get(0).setVisible(true);
        Box chargeBtnB = Box.createHorizontalBox();
        JButton addBtn = new JButton("Add");
        JButton removeBtn = new JButton("Remove");

        addBtn.addActionListener(e -> {
            if(datePickerEndList.get(index).getModel().getValue() != null) {
                Date tempEndDate = (Date) datePickerEndList.get(index).getModel().getValue();
                Date tempStartDate;
                if (index==0){
                    tempStartDate = firstDate;
                }
                else{
                    LocalDate tempDate = LocalDate.parse(datePickerEndList.get(index - 1).getModel().getValue().toString());
                    tempStartDate = Date.valueOf(tempDate.plusDays(2).toString());
                }
                if(tempEndDate.after(tempStartDate)) {
                    datePickerEndList.get(index).setVisible(false);
                    endLabelList.get(index).setText(datePickerEndList.get(index).getModel().getValue().toString());
                    endLabelList.get(index).setVisible(true);
                    index++;
                    LocalDate tempDate = LocalDate.parse(datePickerEndList.get(index - 1).getModel().getValue().toString());
                    startLabelList.get(index).setText(String.valueOf(tempDate.plusDays(1)));
                    if (index == 3) {
                        addBtn.setVisible(false);
                    } else {
                        removeBtn.setVisible(true);
                    }
                    charges.get(index).setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog
                            (null,
                                    "Please select an end date that is after the start date.",
                                    "End date is before start date!",
                                    JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog
                        (null,
                                "Please select a date.",
                                "No Date Selected",
                                JOptionPane.ERROR_MESSAGE);
            }
        });
        removeBtn.addActionListener(e -> {
            charges.get(index).setVisible(false);
            index--;
            endLabelList.get(index).setVisible(false);
            datePickerEndList.get(index).setVisible(true);
            if(index==0){
                removeBtn.setVisible(false);
            }
            else{
                addBtn.setVisible(true);
            }
        });
        chargeBtnB.add(addBtn);
        chargeBtnB.add(removeBtn);
        removeBtn.setVisible(false);
        mainPanel.add(chargeBtnB);
        Box hasBreakfastB = Box.createHorizontalBox();
        JCheckBox hasBreakfast = new JCheckBox("Provides Breakfast?");
        hasBreakfastB.add(hasBreakfast);
        hasBreakfastB.add(Box.createHorizontalGlue());
        mainPanel.add(hasBreakfastB);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        Box nextBtnB = Box.createHorizontalBox();
        JButton nextBtn = new JButton("Next Step");
        nextBtn.setFont(new Font(null, Font.BOLD, 16));
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prop.setEmail(user.getEmail());
                prop.setName(nameT.getText());
                prop.setDescription(descriptionT.getText());
                prop.setLocation(locationT.getText());
                prop.setHasBreakfast(hasBreakfast.isSelected() ? 1 : 0);
                Address address = new Address(addOneT.getText(), addTwoT.getText(), cityT.getText(), postcodeT.getText());
                prop.setAddress(address);

                // Validation
                if (prop.getName().length() <= 100) {
                    if (prop.getName().length() != 0 && FieldValidator.isASCII(prop.getName())) {
                        if (prop.getDescription().length() <= 1000) {
                            if (prop.getDescription().length() != 0 && FieldValidator.isASCII(prop.getDescription())) {
                                if (prop.getLocation().length() <= 100) {
                                    if (prop.getLocation().length() != 0 && FieldValidator.isLatinAndSpace(prop.getLocation())) {
                                        if (prop.getAddress().getAddressLnOne().length() <= 100 && prop.getAddress().getAddressLnTwo().length() <= 100 &&
                                                prop.getAddress().getCity().length() <= 100) {
                                            if (prop.getAddress().getAddressLnOne().length() != 0 && prop.getAddress().getCity().length() != 0 &&
                                                    FieldValidator.isLatinNumSpace(prop.getAddress().getAddressLnOne()) && FieldValidator.isLatinAndSpace(prop.getAddress().getCity())) {
                                                if (prop.getAddress().getPostCode().length() != 0 &&
                                                        FieldValidator.isPostZipValid(prop.getAddress().getPostCode())) {
                                                    Date lastGivenDate = (Date) datePickerEndList.get(index).getModel().getValue();
                                                    if (datePickerEndList.get(index).getModel().getValue() != null) {
                                                        if (lastGivenDate.after(lastDate)) {
                                                            Date tempEndDate = (Date) datePickerEndList.get(index).getModel().getValue();
                                                            Date tempStartDate;
                                                            if (index == 0) {
                                                                tempStartDate = firstDate;
                                                            } else {
                                                                LocalDate tempDate = LocalDate.parse(datePickerEndList.get(index - 1).getModel().getValue().toString());
                                                                tempStartDate = Date.valueOf(tempDate.plusDays(2).toString());
                                                            }
                                                            if (tempEndDate.after(tempStartDate)) {
                                                                boolean isFilledGood =true;
                                                                for (int x = 0; x<index+1; x++){
                                                                    if(pricesPerNight.get(x).getText().length() ==0 || serviceCharges.get(x).getText().length() ==0 || cleaningCharges.get(x).getText().length() ==0){
                                                                        isFilledGood=false;
                                                                    }
                                                                }
                                                                if(isFilledGood) {
                                                                    boolean isPositive = true;
                                                                    for (int x = 0; x < index + 1; x++) {
                                                                        if (Double.parseDouble(pricesPerNight.get(x).getText().replace(",","")) <0 || Double.parseDouble(serviceCharges.get(x).getText().replace(",","")) <0 || Double.parseDouble(cleaningCharges.get(x).getText().replace(",","")) <0) {
                                                                            isPositive = false;
                                                                        }
                                                                    }
                                                                    if (isPositive) {
                                                                        boolean isShort = true;
                                                                        for (int x = 0; x < index + 1; x++) {
                                                                            if (pricesPerNight.get(x).getText().replace(",","").length() > 9 || serviceCharges.get(x).getText().replace(",","").length() > 9 || cleaningCharges.get(x).getText().replace(",","").length() > 9) {
                                                                                isShort = false;
                                                                            }
                                                                        }

                                                                        if (isShort) {
                                                                            for (int i = 0; i < index + 1; i++) {
                                                                                Date startDate;
                                                                                if (i == 0) {
                                                                                    startDate = firstDate;
                                                                                } else {
                                                                                    LocalDate tempDate = LocalDate.parse(datePickerEndList.get(index - 1).getModel().getValue().toString());
                                                                                    startDate = Date.valueOf(tempDate.plusDays(1));
                                                                                }
                                                                                Date endDate = (Date) datePickerEndList.get(i).getModel().getValue();
                                                                                chargeBands.add(new ChargeBand(startDate,
                                                                                        endDate,
                                                                                        Double.parseDouble(pricesPerNight.get(i).getText().replace(",","")),
                                                                                        Double.parseDouble(serviceCharges.get(i).getText().replace(",","")),
                                                                                        Double.parseDouble(cleaningCharges.get(i).getText().replace(",",""))
                                                                                ));
                                                                            }

                                                                            frame.dispose();
                                                                            JFrame nextFrame = FacilitiesSleeping();
                                                                            nextFrame.pack();
                                                                            nextFrame.setLocationRelativeTo(null); // Center the form
                                                                            nextFrame.setVisible(true);
                                                                        }
                                                                        else{
                                                                            JOptionPane.showMessageDialog
                                                                                    (null,
                                                                                            "Please user a number with less digits!",
                                                                                            "Number too long!",
                                                                                            JOptionPane.ERROR_MESSAGE);
                                                                        }
                                                                    }

                                                                    else{
                                                                        JOptionPane.showMessageDialog
                                                                                (null,
                                                                                        "Please make sure all numbers are positive!",
                                                                                        "Negative Number Detected",
                                                                                        JOptionPane.ERROR_MESSAGE);
                                                                    }
                                                                }

                                                                else{
                                                                    JOptionPane.showMessageDialog
                                                                            (null,
                                                                                    "Please make sure all fields are filled",
                                                                                    "Numeric input empty",
                                                                                    JOptionPane.ERROR_MESSAGE);
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog
                                                                        (null,
                                                                                "Please select an end date that is after the start date.",
                                                                                "End date is before start date!",
                                                                                JOptionPane.ERROR_MESSAGE);
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog
                                                                    (null,
                                                                            "Please make sure your last charge band ends 2022-12-31 or later.",
                                                                            "Invalid Entry",
                                                                            JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    }
                                                    else{
                                                        JOptionPane.showMessageDialog
                                                                (null,
                                                                        "Please select a date.",
                                                                        "No Date Selected",
                                                                        JOptionPane.ERROR_MESSAGE);
                                                    }
                                                } else {
                                                    JOptionPane.showMessageDialog
                                                            (null,
                                                                    "Please enter a valid postcode.",
                                                                    "Invalid Entry",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                }
                                            } else {
                                                JOptionPane.showMessageDialog
                                                        (null,
                                                                "Please enter a valid address.",
                                                                "Invalid Entry",
                                                                JOptionPane.ERROR_MESSAGE);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog
                                                    (null,
                                                            "Address Line One, Address Line Two and City can not exceed 100 characters.",
                                                            "Invalid Entry",
                                                            JOptionPane.ERROR_MESSAGE);
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog
                                                (null,
                                                        "Please enter a valid location.",
                                                        "Invalid Entry",
                                                        JOptionPane.ERROR_MESSAGE);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog
                                            (null,
                                                    "Searchable location can not exceed 100 characters.",
                                                    "Invalid Entry",
                                                    JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog
                                        (null,
                                                "Please enter a valid description.",
                                                "Invalid Entry",
                                                JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog
                                    (null,
                                            "Description can not exceed 1000 characters.",
                                            "Invalid Entry",
                                            JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog
                                (null,
                                        "Please enter a valid name.",
                                        "Invalid Entry",
                                        JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog
                            (null,
                                    "Property names can not exceed 25 characters.",
                                    "Invalid Entry",
                                    JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        nextBtnB.add(nextBtn);
        mainPanel.add(nextBtnB);
        scrollPane.setPreferredSize(new Dimension(680, 450));
        frame.add(scrollPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private JFrame FacilitiesSleeping() {
        JFrame frame = new JFrame("Adding Facilities");
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(400, 100));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        Box faB = Box.createHorizontalBox();
        JLabel faL = new JLabel("Sleeping Facilities");
        faL.setFont(new Font(null, Font.BOLD, 16));
        faB.add(faL);
        mainPanel.add(faB);

        Box countB = Box.createHorizontalBox();
        countB.add(Box.createHorizontalGlue());
        countB.setMaximumSize(new Dimension(300, 20));

        JLabel bedroomCountL = new JLabel("Bedroom Count");
        countB.add(bedroomCountL);
        countB.add(Box.createHorizontalGlue());
        JComboBox<Integer> bedroomCountC = new JComboBox<Integer>();
        for (int i = 1; i <= 20; i++) bedroomCountC.addItem(i);
        countB.add(bedroomCountC);
        countB.add(Box.createHorizontalGlue());

        mainPanel.add(countB);

        Box has = Box.createHorizontalBox();
        has.add(Box.createHorizontalGlue());
        JCheckBox hasTowel = new JCheckBox("Towel");
        has.add(hasTowel);
        JCheckBox hasLinen = new JCheckBox("Linen");
        has.add(hasLinen);

        has.add(Box.createHorizontalGlue());
        mainPanel.add(has);

        Box btnB = Box.createHorizontalBox();
        JButton canBtn = new JButton("Cancel");
        canBtn.setFont(new Font(null, Font.BOLD, 16));
        canBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        JButton nextBtn = new JButton("Next");
        nextBtn.setFont(new Font(null, Font.BOLD, 16));
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and add the Sleeping facility
                int bedroomCount = Integer.parseInt(bedroomCountC.getSelectedItem().toString());
                Sleeping sleeping = new Sleeping(hasTowel.isSelected() ? 1 : 0, hasLinen.isSelected() ? 1 : 0);
                prop.setSleeping(sleeping);

                frame.dispose();
                JFrame nextFrame = FacilitiesBedroom(bedroomCount);
                nextFrame.pack();
                nextFrame.setLocationRelativeTo(null);
                nextFrame.setVisible(true);
            }
        });
        btnB.add(canBtn);
        btnB.add(nextBtn);
        mainPanel.add(btnB);
        mainPanel.add(Box.createVerticalGlue());
        frame.add(mainPanel);

        return frame;
    }

    private JFrame FacilitiesBedroom(int bedroomCount) {
        JFrame frame = new JFrame("Adding Facilities");
        JPanel mainPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(620, 150));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        Box faB = Box.createHorizontalBox();
        JLabel faL = new JLabel("Bedroom details");
        faL.setFont(new Font(null, Font.BOLD, 16));
        faB.add(faL);
        mainPanel.add(faB);
        mainPanel.add(Box.createVerticalGlue());

        List<JComboBox> comboBoxes = new ArrayList<>();

        for (int i = 0; i < bedroomCount; i++) {
            Box bedroomB = Box.createHorizontalBox();
            bedroomB.add(Box.createHorizontalGlue());
            bedroomB.setMaximumSize(new Dimension(600, 20));

            JLabel bedroomCountL = new JLabel("Bedroom " + (i + 1) + " :");
            bedroomB.add(bedroomCountL);
            bedroomB.add(Box.createHorizontalGlue());
            JLabel bed1L = new JLabel("Bed 1 ");
            bedroomB.add(bed1L);
            JComboBox<String> bed1C = new JComboBox<String>();
            bed1C.addItem("Single bed");
            bed1C.addItem("Double bed");
            bed1C.addItem("Kingsize bed");
            bed1C.addItem("Bunk bed");
            comboBoxes.add(bed1C);
            bedroomB.add(bed1C);

            JLabel bed2L = new JLabel("Bed 2 ");
            bedroomB.add(bed2L);
            JComboBox<String> bed2C = new JComboBox<String>();
            bed2C.addItem("Single bed");
            bed2C.addItem("Double bed");
            bed2C.addItem("Kingsize bed");
            bed2C.addItem("Bunk bed");
            bed2C.addItem("No second bed");
            bed2C.setSelectedIndex(4);
            comboBoxes.add(bed2C);
            bedroomB.add(bed2C);
            bedroomB.add(Box.createHorizontalGlue());

            mainPanel.add(bedroomB);
        }

        Box btnB = Box.createHorizontalBox();
        JButton prevBtn = new JButton("Previous");
        prevBtn.setFont(new Font(null, Font.BOLD, 16));
        prevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                JFrame nextFrame = FacilitiesSleeping();
                nextFrame.pack();
                nextFrame.setLocationRelativeTo(null);
                nextFrame.setVisible(true);
            }
        });
        JButton nextBtn = new JButton("Next");
        nextBtn.setFont(new Font(null, Font.BOLD, 16));

        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                for (int i = 0; i < comboBoxes.size(); i += 2) {
                    int bed1;
                    if (comboBoxes.get(i).getSelectedItem() == "Single bed") {
                        bed1 = 1;
                    } else {
                        bed1 = 2;
                    }

                    int bed2;
                    if (comboBoxes.get(i + 1).getSelectedItem() == "Single bed") {
                        bed2 = 1;
                    } else if (comboBoxes.get(i + 1).getSelectedItem() == "No second bed") {
                        bed2 = 0;
                    } else {
                        bed2 = 2;
                    }

                    Bedroom newBedroom = new Bedroom(bed1, bed2);
                    prop.getSleeping().addBedroom(newBedroom);
                }

                JFrame nextFrame = FacilitiesBathing();
                nextFrame.pack();
                nextFrame.setLocationRelativeTo(null);
                nextFrame.setVisible(true);
            }
        });

        btnB.add(prevBtn);
        btnB.add(nextBtn);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(btnB);
        mainPanel.add(Box.createVerticalGlue());
        frame.add(mainPanel);
        frame.pack();

        return frame;
    }

    public JFrame FacilitiesBathing() {
        JFrame frame = new JFrame("Adding Facilities");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setPreferredSize(new Dimension(650, 150));
        mainPanel.add(Box.createVerticalGlue());

        Box faB = Box.createHorizontalBox();
        JLabel faL = new JLabel("Bathing Facilities");
        faL.setFont(new Font(null, Font.BOLD, 16));
        faB.add(faL);
        mainPanel.add(faB);

        Box countB = Box.createHorizontalBox();
        countB.setMaximumSize(new Dimension(300, 20));
        countB.add(Box.createHorizontalGlue());
        JLabel bathroomCountL = new JLabel("Bathroom Count");
        countB.add(bathroomCountL);
        countB.add(Box.createHorizontalGlue());
        JComboBox<Integer> bathroomCountC = new JComboBox<Integer>();
        for (int i = 1; i < 20; i++) bathroomCountC.addItem(i);
        countB.add(bathroomCountC);
        countB.add(Box.createHorizontalGlue());
        mainPanel.add(countB);

        Box has = Box.createHorizontalBox();
        has.add(Box.createHorizontalGlue());
        JCheckBox hasHairDrier = new JCheckBox("Hair drier");
        has.add(hasHairDrier);
        JCheckBox hasShampoo = new JCheckBox("Shampoo");
        has.add(hasShampoo);
        JCheckBox hasToiletPaper = new JCheckBox("Toilet paper");
        has.add(hasToiletPaper);
        has.add(Box.createHorizontalGlue());
        mainPanel.add(has);
        Box btnB = Box.createHorizontalBox();
        JButton prevBtn = new JButton("Back");
        prevBtn.setFont(new Font(null, Font.BOLD, 16));
        prevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                JFrame nextFrame = FacilitiesBedroom(prop.getSleeping().getBedroomCount());
                nextFrame.pack();
                nextFrame.setLocationRelativeTo(null);
                nextFrame.setVisible(true);
            }
        });
        JButton nextBtn = new JButton("Next");
        nextBtn.setFont(new Font(null, Font.BOLD, 16));
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int bathroomCount = Integer.parseInt(bathroomCountC.getSelectedItem().toString());
                Bathing bathing = new Bathing(hasHairDrier.isSelected() ? 1 : 0,
                        hasShampoo.isSelected() ? 1 : 0, hasToiletPaper.isSelected() ? 1 : 0);
                prop.setBathing(bathing);

                frame.dispose();
                JFrame nextFrame = FacilitiesBathroom(bathroomCount);
                nextFrame.pack();
                nextFrame.setLocationRelativeTo(null);
                nextFrame.setVisible(true);
            }
        });
        btnB.add(prevBtn);
        btnB.add(nextBtn);
        mainPanel.add(btnB);
        mainPanel.add(Box.createVerticalGlue());

        frame.add(mainPanel);
        return frame;
    }

    public JFrame FacilitiesBathroom(int bathroomCount) {
        JFrame frame = new JFrame("Adding Facilities");
        JPanel mainPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(620, 150));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        Box faB = Box.createHorizontalBox();
        JLabel faL = new JLabel("Bathroom details");
        faL.setFont(new Font(null, Font.BOLD, 16));
        faB.add(faL);
        mainPanel.add(faB);
        mainPanel.add(Box.createVerticalGlue());

        List<JCheckBox> checkBoxes = new ArrayList<>();
        for (int i = 0; i < bathroomCount; i++) {
            Box bathroomB = Box.createHorizontalBox();
            bathroomB.add(Box.createHorizontalGlue());
            bathroomB.setMaximumSize(new Dimension(600, 20));
            JLabel bathroomCountL = new JLabel("Bathroom " + (i + 1) + " :");
            bathroomB.add(bathroomCountL);
            bathroomB.add(Box.createHorizontalGlue());
            JCheckBox hasToilet = new JCheckBox("Toilet");
            checkBoxes.add(hasToilet);
            bathroomB.add(hasToilet);
            JCheckBox hasBath = new JCheckBox("Bath");
            checkBoxes.add(hasBath);
            bathroomB.add(hasBath);
            JCheckBox hasShower = new JCheckBox("Shower");
            checkBoxes.add(hasShower);
            bathroomB.add(hasShower);
            JCheckBox isShared = new JCheckBox("Shared");
            checkBoxes.add(isShared);
            bathroomB.add(isShared);
            bathroomB.add(Box.createHorizontalGlue());
            mainPanel.add(bathroomB);
        }

        Box btnB = Box.createHorizontalBox();
        JButton prevBtn = new JButton("Back");
        prevBtn.setFont(new Font(null, Font.BOLD, 16));
        prevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                JFrame nextFrame = FacilitiesBathing();
                nextFrame.pack();
                nextFrame.setLocationRelativeTo(null);
                nextFrame.setVisible(true);
            }
        });
        JButton nextBtn = new JButton("Next");
        nextBtn.setFont(new Font(null, Font.BOLD, 16));
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

                for (int i = 0; i < checkBoxes.size(); i += 4) {
                    Bathroom bathroom = new Bathroom();
                    if (checkBoxes.get(i).isSelected()) bathroom.setHasToilet(1);
                    if (checkBoxes.get(i + 1).isSelected()) bathroom.setHasBath(1);
                    if (checkBoxes.get(i + 2).isSelected()) bathroom.setHasShower(1);
                    if (checkBoxes.get(i + 3).isSelected()) bathroom.setShared(1);
                    prop.getBathing().addBathroom(bathroom);

                }

                JFrame nextFrame = FacilitiesKitchen();
                nextFrame.pack();
                nextFrame.setLocationRelativeTo(null);
                nextFrame.setVisible(true);
            }
        });
        btnB.add(prevBtn);
        btnB.add(nextBtn);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(btnB);
        mainPanel.add(Box.createVerticalGlue());
        frame.add(mainPanel);
        frame.pack();

        return frame;
    }


    public JFrame FacilitiesKitchen() {
        JFrame frame = new JFrame("Adding Facilities");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createVerticalGlue());

        Box faB = Box.createHorizontalBox();
        JLabel faL = new JLabel("Kitchen Facilities");
        faL.setFont(new Font(null, Font.BOLD, 16));
        faB.add(faL);
        mainPanel.add(faB);

        Box has1 = Box.createHorizontalBox();
        has1.add(Box.createHorizontalGlue());
        JCheckBox hasFridge = new JCheckBox("Fridge");
        has1.add(hasFridge);
        JCheckBox hasMicro = new JCheckBox("Microwave");
        has1.add(hasMicro);
        JCheckBox hasOven = new JCheckBox("Oven");
        has1.add(hasOven);
        JCheckBox hasStove = new JCheckBox("Stove");
        has1.add(hasStove);
        JCheckBox hasDishwasher = new JCheckBox("Dishwasher");
        has1.add(hasDishwasher);
        has1.add(Box.createHorizontalGlue());

        Box has2 = Box.createHorizontalBox();
        has2.add(Box.createHorizontalGlue());
        JCheckBox hasTableware = new JCheckBox("Tableware");
        has2.add(hasTableware);
        JCheckBox hasCookware = new JCheckBox("Cookware");
        has2.add(hasCookware);
        JCheckBox hasBasics = new JCheckBox("Basics");
        has2.add(hasBasics);
        has2.add(Box.createHorizontalGlue());

        mainPanel.add(has1);
        mainPanel.add(has2);

        Box btnB = Box.createHorizontalBox();
        JButton prevBtn = new JButton("Back");
        prevBtn.setFont(new Font(null, Font.BOLD, 16));
        prevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                JFrame nextFrame = FacilitiesBathroom(prop.getBathing().getBathroomCount());
                nextFrame.pack();
                nextFrame.setLocationRelativeTo(null);
                nextFrame.setVisible(true);
            }
        });
        JButton nextBtn = new JButton("Next");
        nextBtn.setFont(new Font(null, Font.BOLD, 16));
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Kitchen newKitchen = new Kitchen(hasFridge.isSelected() ? 1 : 0, hasMicro.isSelected() ? 1 : 0,
                        hasOven.isSelected() ? 1 : 0, hasStove.isSelected() ? 1 : 0, hasDishwasher.isSelected() ? 1 : 0,
                        hasTableware.isSelected() ? 1 : 0, hasCookware.isSelected() ? 1 : 0, hasBasics.isSelected() ? 1 : 0);
                prop.setKitchen(newKitchen);

                frame.dispose();
                JFrame nextFrame = FacilitiesLiving();
                nextFrame.pack();
                nextFrame.setLocationRelativeTo(null);
                nextFrame.setVisible(true);
            }
        });
        btnB.add(prevBtn);
        btnB.add(nextBtn);
        mainPanel.add(btnB);
        mainPanel.add(Box.createVerticalGlue());

        frame.add(mainPanel);

        return frame;
    }

    public JFrame FacilitiesLiving() {
        JFrame frame = new JFrame("Adding Facilities");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createVerticalGlue());

        Box faB = Box.createHorizontalBox();
        JLabel faL = new JLabel("Living Facilities");
        faL.setFont(new Font(null, Font.BOLD, 16));
        faB.add(faL);
        mainPanel.add(faB);

        Box has1 = Box.createHorizontalBox();
        has1.add(Box.createHorizontalGlue());
        JCheckBox hasWLAN = new JCheckBox("WiFi");
        has1.add(hasWLAN);
        JCheckBox hasTV = new JCheckBox("TV");
        has1.add(hasTV);
        JCheckBox hasSatellite = new JCheckBox("Satellite");
        has1.add(hasSatellite);
        JCheckBox hasStreaming = new JCheckBox("Streaming");
        has1.add(hasStreaming);
        JCheckBox hasDVD = new JCheckBox("DVD");
        has1.add(hasDVD);
        has1.add(Box.createHorizontalGlue());

        Box has2 = Box.createHorizontalBox();
        has2.add(Box.createHorizontalGlue());
        JCheckBox hasBoardGames = new JCheckBox("Board games");
        has2.add(hasBoardGames);
        has2.add(Box.createHorizontalGlue());

        mainPanel.add(has1);
        mainPanel.add(has2);

        Box btnB = Box.createHorizontalBox();
        JButton prevBtn = new JButton("Back");
        prevBtn.setFont(new Font(null, Font.BOLD, 16));
        prevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                JFrame nextFrame = FacilitiesKitchen();
                nextFrame.pack();
                nextFrame.setLocationRelativeTo(null);
                nextFrame.setVisible(true);
            }
        });
        JButton nextBtn = new JButton("Next");
        nextBtn.setFont(new Font(null, Font.BOLD, 16));
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Living newLiving = new Living(hasWLAN.isSelected() ? 1 : 0, hasTV.isSelected() ? 1 : 0,
                        hasSatellite.isSelected() ? 1 : 0, hasStreaming.isSelected() ? 1 : 0, hasDVD.isSelected() ? 1 : 0,
                        hasBoardGames.isSelected() ? 1 : 0);
                prop.setLiving(newLiving);

                frame.dispose();
                JFrame nextFrame = FacilitiesUtility();
                nextFrame.pack();
                nextFrame.setLocationRelativeTo(null); // Center the form
                nextFrame.setVisible(true);
            }
        });
        btnB.add(prevBtn);
        btnB.add(nextBtn);
        mainPanel.add(btnB);
        mainPanel.add(Box.createVerticalGlue());

        frame.add(mainPanel);

        return frame;
    }


    public JFrame FacilitiesUtility() {
        JFrame frame = new JFrame("Adding Facilities");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createVerticalGlue());

        Box faB = Box.createHorizontalBox();
        JLabel faL = new JLabel("Utility Facilities");
        faL.setFont(new Font(null, Font.BOLD, 16));
        faB.add(faL);
        mainPanel.add(faB);

        Box has1 = Box.createHorizontalBox();
        has1.add(Box.createHorizontalGlue());
        JCheckBox hasHeating = new JCheckBox("Central heating");
        has1.add(hasHeating);
        JCheckBox hasWasher = new JCheckBox("Washing machine");
        has1.add(hasWasher);
        JCheckBox hasClothesDrier = new JCheckBox("Tumble drier");
        has1.add(hasClothesDrier);
        JCheckBox hasExtinguisher = new JCheckBox("Fire extinguisher");
        has1.add(hasExtinguisher);
        has1.add(Box.createHorizontalGlue());

        Box has2 = Box.createHorizontalBox();
        has2.add(Box.createHorizontalGlue());
        JCheckBox hasSmokeAlarm = new JCheckBox("Smoke alarm");
        has2.add(hasSmokeAlarm);
        JCheckBox hasFirstAid = new JCheckBox("First aid kit");
        has2.add(hasFirstAid);
        has2.add(Box.createHorizontalGlue());

        mainPanel.add(has1);
        mainPanel.add(has2);

        Box btnB = Box.createHorizontalBox();
        JButton prevBtn = new JButton("Back");
        prevBtn.setFont(new Font(null, Font.BOLD, 16));
        prevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                JFrame nextFrame = FacilitiesLiving();
                nextFrame.pack();
                nextFrame.setLocationRelativeTo(null);
                nextFrame.setVisible(true);
            }
        });
        JButton nextBtn = new JButton("Next");
        nextBtn.setFont(new Font(null, Font.BOLD, 16));
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utility newUtility = new Utility(hasHeating.isSelected() ? 1 : 0, hasWasher.isSelected() ? 1 : 0,
                        hasClothesDrier.isSelected() ? 1 : 0, hasExtinguisher.isSelected() ? 1 : 0,
                        hasSmokeAlarm.isSelected() ? 1 : 0, hasFirstAid.isSelected() ? 1 : 0);
                prop.setUtility(newUtility);

                frame.dispose();
                JFrame nextFrame = FacilitiesOutdoors();
                nextFrame.pack();
                nextFrame.setLocationRelativeTo(null); // Center the form
                nextFrame.setVisible(true);
            }
        });
        btnB.add(prevBtn);
        btnB.add(nextBtn);
        mainPanel.add(btnB);
        mainPanel.add(Box.createVerticalGlue());

        frame.add(mainPanel);

        return frame;
    }

    public JFrame FacilitiesOutdoors() {
        JFrame frame = new JFrame("Adding Facilities");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createVerticalGlue());

        Box faB = Box.createHorizontalBox();
        JLabel faL = new JLabel("Outdoors Facilities");
        faL.setFont(new Font(null, Font.BOLD, 16));
        faB.add(faL);
        mainPanel.add(faB);

        Box has1 = Box.createHorizontalBox();
        has1.add(Box.createHorizontalGlue());
        JCheckBox hasOnSiteParking = new JCheckBox("On-site parking");
        has1.add(hasOnSiteParking);
        JCheckBox hasRoadParking = new JCheckBox("Road parking");
        has1.add(hasRoadParking);
        JCheckBox hasCarPark = new JCheckBox("Car park");
        has1.add(hasCarPark);
        has1.add(Box.createHorizontalGlue());

        Box has2 = Box.createHorizontalBox();
        has2.add(Box.createHorizontalGlue());
        JCheckBox hasPatio = new JCheckBox("Patio");
        has2.add(hasPatio);
        JCheckBox hasBBQ = new JCheckBox("Barbeque");
        has2.add(hasBBQ);
        has2.add(Box.createHorizontalGlue());

        mainPanel.add(has1);
        mainPanel.add(has2);

        Box btnB = Box.createHorizontalBox();
        JButton prevBtn = new JButton("Back");
        prevBtn.setFont(new Font(null, Font.BOLD, 16));
        prevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                JFrame nextFrame = FacilitiesUtility();
                nextFrame.pack();
                nextFrame.setLocationRelativeTo(null);
                nextFrame.setVisible(true);
            }
        });
        JButton finBtn = new JButton("Finish");
        finBtn.setFont(new Font(null, Font.BOLD, 16));
        finBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Outdoors newOutdoors = new Outdoors(hasOnSiteParking.isSelected() ? 1 : 0, hasRoadParking.isSelected() ? 1 : 0,
                        hasCarPark.isSelected() ? 1 : 0, hasPatio.isSelected() ? 1 : 0, hasBBQ.isSelected() ? 1 : 0);
                prop.setOutdoors(newOutdoors);

                String addressQuery = "insert into addresses (addressLnOne, addressLnTwo, city, postCode)" +
                        "values (?, ?, ?, ?)";

                try (Connection con = DriverManager.getConnection(
                        "jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365")) {
                    PreparedStatement psCheck = con.prepareStatement
                            ("SELECT addressLnOne FROM addresses WHERE addressLnOne = ? AND postCode = ?");
                    psCheck.setString(1, prop.getAddress().getAddressLnOne());
                    psCheck.setString(2, prop.getAddress().getPostCode());
                    ResultSet rs = psCheck.executeQuery();
                    if (!rs.next()) {
                        PreparedStatement preparedAddress = con.prepareStatement(addressQuery);

                        preparedAddress.setString(1, prop.getAddress().getAddressLnOne());
                        preparedAddress.setString(2, prop.getAddress().getAddressLnTwo());
                        preparedAddress.setString(3, prop.getAddress().getCity());
                        preparedAddress.setString(4, prop.getAddress().getPostCode());

                        preparedAddress.executeUpdate();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog
                            (null,
                                    "There was an error adding the address.\nPlease contact an administrator.",
                                    "Address error",
                                    JOptionPane.ERROR_MESSAGE);
                    throw new IllegalStateException("Connection Failed!", ex);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog
                            (null,
                                    "There was an error adding the address.\nPlease contact an administrator.",
                                    "Address error",
                                    JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

                String propertyQuery = "INSERT INTO properties " +
                        "(name, description, location, addressLnOne, addressLnTwo, city, postCode, " +
                        "hasBreakfast, hasTowel, hasLinen, hasHairDrier, hasShampoo, hasToiletPaper, hasFridge, " +
                        "hasMicro, hasOven, hasStove, hasDishwasher, hasTableware, hasCookware, hasBasics, hasWLAN, " +
                        "hasTV, hasSatellite, hasStreaming, hasDVD, hasBoardGames, hasHeating, hasWasher, " +
                        "hasClothesDrier, hasExtinguisher, hasSmokeAlarm, hasFirstAid, hasOnSiteParking, " +
                        "hasRoadParking, hasCarPark, hasPatio, hasBBQ, email)" +
                        " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (Connection con = DriverManager.getConnection
                        ("jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365")) {
                    PreparedStatement psProp = con.prepareStatement(propertyQuery, Statement.RETURN_GENERATED_KEYS);

                    psProp.setString(1, prop.getName());
                    psProp.setString(2, prop.getDescription());
                    psProp.setString(3, prop.getLocation());
                    psProp.setString(4, prop.getAddress().getAddressLnOne());
                    psProp.setString(5, prop.getAddress().getAddressLnTwo());
                    psProp.setString(6, prop.getAddress().getCity());
                    psProp.setString(7, prop.getAddress().getPostCode());
                    psProp.setInt(8, prop.getHasBreakfast());

                    psProp.setInt(9, prop.getSleeping().getHasTowel());
                    psProp.setInt(10, prop.getSleeping().getHasLinen());

                    psProp.setInt(11, prop.getBathing().getHasHairDryer());
                    psProp.setInt(12, prop.getBathing().getHasShampoo());
                    psProp.setInt(13, prop.getBathing().getHasToiletPaper());

                    psProp.setInt(14, prop.getKitchen().getHasFridge());
                    psProp.setInt(15, prop.getKitchen().getHasMicro());
                    psProp.setInt(16, prop.getKitchen().getHasOven());
                    psProp.setInt(17, prop.getKitchen().getHasStove());
                    psProp.setInt(18, prop.getKitchen().getHasDishwasher());
                    psProp.setInt(19, prop.getKitchen().getHasTableware());
                    psProp.setInt(20, prop.getKitchen().getHasCookware());
                    psProp.setInt(21, prop.getKitchen().getHasBasics());

                    psProp.setInt(22, prop.getLiving().getHasWLAN());
                    psProp.setInt(23, prop.getLiving().getHasTV());
                    psProp.setInt(24, prop.getLiving().getHasSatellite());
                    psProp.setInt(25, prop.getLiving().getHasStreaming());
                    psProp.setInt(26, prop.getLiving().getHasDVD());
                    psProp.setInt(27, prop.getLiving().getHasBoardGames());

                    psProp.setInt(28, prop.getUtility().getHasHeating());
                    psProp.setInt(29, prop.getUtility().getHasWasher());
                    psProp.setInt(30, prop.getUtility().getHasClothesDryer());
                    psProp.setInt(31, prop.getUtility().getHasExtinguisher());
                    psProp.setInt(32, prop.getUtility().getHasSmokeAlarm());
                    psProp.setInt(33, prop.getUtility().getHasFirstAid());

                    psProp.setInt(34, prop.getOutdoors().getHasOnSiteParking());
                    psProp.setInt(35, prop.getOutdoors().getHasRoadParking());
                    psProp.setInt(36, prop.getOutdoors().getHasCarPark());
                    psProp.setInt(37, prop.getOutdoors().getHasPatio());
                    psProp.setInt(38, prop.getOutdoors().getHasBBQ());

                    psProp.setString(39, user.getEmail());
                    psProp.executeUpdate();

                    // Get the property ID for adding to the chargebands
                    ResultSet keys = psProp.getGeneratedKeys();
                    while (keys.next()) {
                        propertyID = keys.getInt(1);
                    }
                    prop.setPropertyID(propertyID);

                    // Add the property ID to the chargeband and insert into database
                    for (ChargeBand c : chargeBands) {
                        c.setPropertyID(propertyID);
                        c.createChargeBand();
                        prop.addChargeBand(c);
                    }

                    // Add the bedrooms
                    String bedQuery = "INSERT INTO bedrooms " +
                            "(bedOne, bedTwo, propertyID) VALUES (?, ?, ?)";
                    PreparedStatement psBed = con.prepareStatement(bedQuery);
                    for (Bedroom bedroom : prop.getSleeping().getBedrooms()) {
                        psBed.setInt(1, bedroom.getBed1());
                        psBed.setInt(2, bedroom.getBed2());
                        psBed.setInt(3, prop.getPropertyID());
                        psBed.executeUpdate();
                    }

                    // Add the bathrooms
                    String bathQuery = "INSERT INTO bathrooms " +
                            "(hasToilet, hasBath, hasShower, isShared, propertyID) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement psBath = con.prepareStatement(bathQuery);
                    for (Bathroom bathroom : prop.getBathing().getBathrooms()) {
                        psBath.setInt(1, bathroom.getHasToilet());
                        psBath.setInt(2, bathroom.getHasBath());
                        psBath.setInt(3, bathroom.getHasShower());
                        psBath.setInt(4, bathroom.getShared());
                        psBath.setInt(5, prop.getPropertyID());
                        psBath.executeUpdate();
                    }

                } catch (SQLException ex) {
                        JOptionPane.showMessageDialog
                                (null,
                                        "There was an error adding the property.\nPlease contact an administrator.",
                                        "Error Message",
                                        JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                        throw new IllegalStateException("Connection Failed!", ex);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog
                                (null,
                                        "There was an error adding the property.\nPlease contact an administrator.",
                                        "Error Message",
                                        JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }

                    frame.dispose();
                    // This returns 0 if OK was clicked, -1 if cancel
                    int res = JOptionPane.showOptionDialog(null,
                            "Your property has been added successfully!\n" +
                                    "Please check the Bookings page for Booking Requests.\n" +
                                    "Thank you for using Homebreaks."
                            , "Property added successfully!",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, PANEL.imgTransformed,
                            null, null);
                    if (res == 0 || res == -1) {
                        frame.dispose();

                        // This refreshes the search list with the new property
                        PANEL.searchListBtn.doClick();
                    }

            }
        });
        btnB.add(prevBtn);
        btnB.add(finBtn);
        mainPanel.add(btnB);
        mainPanel.add(Box.createVerticalGlue());
        frame.add(mainPanel);

        return frame;
    }
}
