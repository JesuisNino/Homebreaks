package com.homebreaks.gui;

import com.homebreaks.main.Person;
import com.homebreaks.util.FieldValidator;
import com.homebreaks.util.SHAHashPassword;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Locale;

public class RoleChoose extends JFrame {
    protected JPanel mainPanel;
    private JCheckBox guestCheckBox;
    private JCheckBox hostCheckBox;
    private JButton finishBtn;
    private JTextField publicName;

    // Make a pretty icon for dialog
    static ImageIcon imageIcon = new ImageIcon("../res/homebreaks_logo.png");
    static Image img = imageIcon.getImage();
    static Image scaledImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    static ImageIcon imgTransformed = new ImageIcon(scaledImg);

    public RoleChoose(JFrame frame, Person newUser) {
        finishBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String publicNameText = publicName.getText();

                if ((!guestCheckBox.isSelected()) && (!hostCheckBox.isSelected())) {
                    JOptionPane.showMessageDialog(null,
                            "Please select a role.",
                            "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    if (publicNameText.length() > 0) {
                        if (FieldValidator.isNameValid(publicNameText)) {
                            JOptionPane.showMessageDialog(null,
                                    "Please enter a valid name",
                                    "Error Message",
                                    JOptionPane.ERROR_MESSAGE);

                            try (Connection connection = DriverManager.getConnection
                                    ("jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365")) {

                                // Get salt from the database
                                byte[] salt;
                                PreparedStatement saltStatement = connection.prepareStatement("SELECT salt FROM persons WHERE email = ?");
                                saltStatement.setString(1, newUser.getEmail());
                                ResultSet saltRs = saltStatement.executeQuery();
                                if (saltRs.next()) {
                                    salt = saltRs.getBytes("salt");
                                } else {
                                    salt = new byte[16];
                                }

                                // Create user
                                PreparedStatement psCheck = connection.prepareStatement
                                        ("SELECT email FROM persons WHERE email = ? AND password = ?");
                                psCheck.setString(1, newUser.getEmail());
                                psCheck.setString(2, SHAHashPassword.hashPassword(newUser.getPassword(), salt));
                                ResultSet rs = psCheck.executeQuery();
                                if (rs.next()) {
                                    JOptionPane.showOptionDialog(null,
                                            "User already exists.",
                                            "Error Message", JOptionPane.DEFAULT_OPTION,
                                            JOptionPane.ERROR_MESSAGE, null, null, null);
                                } else {
                                    salt = SHAHashPassword.getSalt();
                                    String hashedPassword = SHAHashPassword.hashPassword(newUser.getPassword(), salt);

                                    PreparedStatement psCreate = connection.prepareStatement
                                            ("INSERT INTO persons (email, password, salt, title, forename, surname, mobile)" +
                                                    "VALUES (?, ?, ?, ?, ?, ?, ?);");
                                    psCreate.setString(1, (newUser.getEmail()));
                                    psCreate.setString(2, hashedPassword);
                                    psCreate.setBytes(3, salt);
                                    psCreate.setString(4, (newUser.getTitle()));
                                    psCreate.setString(5, (newUser.getForename()));
                                    psCreate.setString(6, (newUser.getSurname()));
                                    psCreate.setString(7, (newUser.getMobile()));

                                    PreparedStatement psAddress = connection.prepareStatement
                                            ("INSERT INTO addresses (addressLnOne, addressLnTwo, city, postCode) VALUES (?, ?, ?, ?);");
                                    psAddress.setString(1, newUser.getAddress().getAddressLnOne());
                                    psAddress.setString(2, newUser.getAddress().getAddressLnTwo());
                                    psAddress.setString(3, newUser.getAddress().getCity());
                                    psAddress.setString(4, newUser.getAddress().getPostCode());

                                    PreparedStatement linkAddress = connection.prepareStatement
                                            ("UPDATE persons SET addressLnOne = ?, postCode = ? WHERE email = ?");
                                    linkAddress.setString(1, newUser.getAddress().getAddressLnOne());
                                    linkAddress.setString(2, newUser.getAddress().getPostCode());
                                    linkAddress.setString(3, newUser.getEmail());

                                    psCreate.execute();
                                    psAddress.execute();
                                    linkAddress.execute();
                                }
                            } catch (SQLException | NoSuchAlgorithmException ex) {
                                ex.printStackTrace();
                                throw new IllegalStateException("Connection Failed!", ex);
                            }

                            try (Connection con = DriverManager.getConnection
                                    ("jdbc:mysql://stusql.dcs.shef.ac.uk/team055", "team055", "c471f365")) {
                                String updatePersons = "UPDATE persons SET isHost = ?, isGuest = ? WHERE email = ?;";
                                String createHost = "INSERT INTO hosts (publicName, isSuperHost, email) VALUES (?, ?, ?);";
                                String createGuest = "INSERT INTO guests (publicName, email) VALUES (?, ?);";

                                PreparedStatement updatePersonsPS = con.prepareStatement(updatePersons);
                                PreparedStatement updateHostsPS = con.prepareStatement(createHost);
                                PreparedStatement updateGuestsPS = con.prepareStatement(createGuest);

                                // Check which boxes are ticked and update the corresponding tables
                                if (guestCheckBox.isSelected() && hostCheckBox.isSelected()) {
                                    updatePersonsPS.setInt(1, 1);
                                    updatePersonsPS.setInt(2, 1);
                                    updatePersonsPS.setString(3, newUser.getEmail());
                                    updateHostsPS.setString(1, publicNameText);
                                    updateHostsPS.setInt(2, 0);
                                    updateHostsPS.setString(3, newUser.getEmail());
                                    updateGuestsPS.setString(1, publicNameText);
                                    updateGuestsPS.setString(2, newUser.getEmail());
                                    updatePersonsPS.executeUpdate();
                                    updateHostsPS.executeUpdate();
                                    updateGuestsPS.executeUpdate();
                                } else if (guestCheckBox.isSelected()) {
                                    updatePersonsPS.setInt(1, 0);
                                    updatePersonsPS.setInt(2, 1);
                                    updatePersonsPS.setString(3, newUser.getEmail());
                                    updateGuestsPS.setString(1, publicNameText);
                                    updateGuestsPS.setString(2, newUser.getEmail());
                                    updatePersonsPS.executeUpdate();
                                    updateGuestsPS.executeUpdate();
                                } else if (hostCheckBox.isSelected()) {
                                    updatePersonsPS.setInt(1, 1);
                                    updatePersonsPS.setInt(2, 0);
                                    updatePersonsPS.setString(3, newUser.getEmail());
                                    updateHostsPS.setString(1, publicNameText);
                                    updateHostsPS.setInt(2, 0);
                                    updateHostsPS.setString(3, newUser.getEmail());
                                    updatePersonsPS.executeUpdate();
                                    updateHostsPS.executeUpdate();
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            JOptionPane.showOptionDialog(null,
                                    "Registration successful! Please login to access your account.",
                                    "Success!", JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.ERROR_MESSAGE, imgTransformed, null, null);

                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Please enter a valid name",
                                    "Error Message",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Please enter a name",
                                "Error Message",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 6, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        finishBtn = new JButton();
        Font finishBtnFont = this.$$$getFont$$$(null, -1, 20, finishBtn.getFont());
        if (finishBtnFont != null) finishBtn.setFont(finishBtnFont);
        finishBtn.setText("Finish");
        panel1.add(finishBtn, new GridConstraints(4, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 25), null, 0, false));
        publicName = new JTextField();
        publicName.setText("");
        panel1.add(publicName, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(70, -1), null, 0, false));
        guestCheckBox = new JCheckBox();
        guestCheckBox.setEnabled(true);
        Font guestCheckBoxFont = this.$$$getFont$$$(null, -1, 18, guestCheckBox.getFont());
        if (guestCheckBoxFont != null) guestCheckBox.setFont(guestCheckBoxFont);
        guestCheckBox.setText("Become a guest");
        panel1.add(guestCheckBox, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(323, 27), null, 0, false));
        hostCheckBox = new JCheckBox();
        Font hostCheckBoxFont = this.$$$getFont$$$(null, -1, 18, hostCheckBox.getFont());
        if (hostCheckBoxFont != null) hostCheckBox.setFont(hostCheckBoxFont);
        hostCheckBox.setText("Become a host");
        panel1.add(hostCheckBox, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(323, 27), null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.BOLD, 18, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Public Name");
        panel1.add(label1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

    /**
     * @noinspection ALL
     */
    /*
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

     */

}