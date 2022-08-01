package com.homebreaks.gui;

import com.homebreaks.main.Booking;
import com.homebreaks.main.Person;
import com.homebreaks.main.Property;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;

/**
 * Abstract main glass for GUI, initalising GUI components
 *
 * @version 1.0
 *
 * @author team055
 *
 */

public abstract class AbstractHomebreaksPanel extends JPanel {
    protected Person user;
    protected List<Property> allProperties = new ArrayList<Property>();
    protected List<Booking> bookings = new ArrayList<Booking>(); // Will be used later
    protected final JButton logInBtn = new JButton("Login");
    protected final JButton registerBtn = new JButton("Register");
    protected final JButton logOutBtn = new JButton("Logout");
    protected final JButton searchBtn = new JButton("Search");
    protected final JButton bookingBtn = new JButton("Booking List");
    protected final JButton addPropBtn = new JButton("Add a Property");
    protected final JButton searchListBtn = new JButton("Search List");
    protected final JButton switchRolesBtn = new JButton("Switch Roles");
    protected JTextField cityName = new JTextField();
    protected SqlDateModel model = new SqlDateModel();
    protected SqlDateModel model2 = new SqlDateModel();
    protected JDatePanelImpl datePanel = new JDatePanelImpl(model);
    protected JDatePanelImpl datePanel2 = new JDatePanelImpl(model2);
    protected JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
    protected JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2);
    protected JComboBox guestNum = new JComboBox();
    protected Box resultList = Box.createHorizontalBox();
    protected JPanel menuPanel = new JPanel();
    protected JPanel searchPanel = new JPanel();
    protected JPanel bookingPanel = new JPanel();
    protected JLabel roleLabel = new JLabel();
    protected List<JButton> reviewButtons = new ArrayList<>();

    // 0 represents Guest role view, 1 represents Host role - used to handle dual-role users
    protected int switchRoleFlag = 0;

    // 0 represents Search view, 1 represents Bookings view - used to handle dual-role users
    protected int mainListFlag = 0;

    // Make a pretty icon for dialog
    URL url = getClass().getResource("../res/homebreaks_logo.png");
    Image img = new ImageIcon(url).getImage();
    Image scaledImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    public ImageIcon imgTransformed = new ImageIcon(scaledImg);

    // Constructor
    public AbstractHomebreaksPanel(Person user) {
        this.user = user;

        Font btnFont = new Font(null,Font.BOLD,14);
        logInBtn.setFont(btnFont);
        registerBtn.setFont(btnFont);
        logOutBtn.setFont(btnFont);
        logOutBtn.setForeground(Color.RED);
        searchBtn.setFont(btnFont);
        bookingBtn.setFont(btnFont);
        addPropBtn.setFont(btnFont);
        searchListBtn.setFont(btnFont);
        switchRolesBtn.setFont(btnFont);

        // Set up the menu bar
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.X_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        roleLabel.setText("Welcome, Enquirer!");
        roleLabel.setFont(new Font(null,Font.BOLD,18));
        menuPanel.add(roleLabel);
        menuPanel.add(Box.createHorizontalGlue());
        menuPanel.add(switchRolesBtn);
        switchRolesBtn.setVisible(false);
        menuPanel.add(logInBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(2, 0)));
        menuPanel.add(registerBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(2, 0)));
        menuPanel.add(bookingBtn);
        bookingBtn.setVisible(false);
        menuPanel.add(Box.createRigidArea(new Dimension(2, 0)));
        menuPanel.add(searchListBtn);
        searchListBtn.setVisible(false);
        menuPanel.add(Box.createRigidArea(new Dimension(2, 0)));
        menuPanel.add(addPropBtn);
        addPropBtn.setVisible(false);
        menuPanel.add(Box.createRigidArea(new Dimension(2, 0)));
        menuPanel.add(logOutBtn);
        logOutBtn.setVisible(false);

        // Set up the Search bar
        JLabel searchTitle = new JLabel("Search");
        model.setSelected(true);
        model2.setSelected(true);
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        Box searchBox = Box.createHorizontalBox(); //create searchBox box
        searchTitle.setFont(new Font(null, Font.BOLD, 20));
        searchPanel.add(searchTitle);
        searchPanel.add(Box.createRigidArea(new Dimension(0,5)));
        searchBox.add(new JLabel("Location"));
        searchBox.add(cityName);
        searchBox.add(Box.createHorizontalGlue());
        searchBox.add(new JLabel("Start Date"));
        model.setSelected(false);
        searchBox.add(datePicker);
        searchBox.add(Box.createHorizontalGlue());
        searchBox.add(new JLabel("End Date"));
        model2.setSelected(false);
        searchBox.add(datePicker2);
        searchBox.add(Box.createHorizontalGlue());

        searchBox.add(new JLabel("Guest Number"));
        guestNum.addItem("Any");
        guestNum.addItem("1");
        guestNum.addItem("2");
        guestNum.addItem("3");
        guestNum.addItem("4");
        guestNum.addItem("5");
        guestNum.addItem("6");
        guestNum.addItem("7+");
        guestNum.setSelectedIndex(0);
        searchBox.add(guestNum);

        // Search button
        searchBox.add(Box.createHorizontalGlue());
        resultList.add(getResult("Any", "Any", "Any", "Any"));
        resultList.add(Box.createRigidArea(new Dimension(10, 5)));
        searchBox.add(searchBtn);
        searchBox.setPreferredSize(new Dimension(300, 20));
        searchBox.setMaximumSize(new Dimension(1000, 20));
        searchPanel.add(searchBox);
        searchPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        searchPanel.add(resultList);

        // Bookings panel - WIP
        bookingPanel.setLayout(new BoxLayout(bookingPanel,BoxLayout.Y_AXIS));
        bookingPanel.add(getBooking());
        bookingPanel.setVisible(false);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(menuPanel);
        this.add(searchPanel);
        this.add(bookingPanel);
        addListeners();

    }

    // Functions implemented in HomebreaksPanel.java
    public abstract void addListeners();
    public abstract void setUser(Person user);
    public abstract JScrollPane getResult(String location, String start, String end, String guests);
    public abstract void updateMenu();
    public abstract void updateBookings();
    public abstract JScrollPane getBooking();
}
