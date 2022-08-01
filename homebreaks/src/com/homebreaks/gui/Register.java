package com.homebreaks.gui;

import com.homebreaks.main.Address;
import com.homebreaks.main.Person;
import com.homebreaks.util.FieldValidator;
import com.homebreaks.util.SHAHashPassword;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Locale;

/**
 * Register-associated class.
 *
 * @author team055
 * @version 1.0
 * @date 05.12.2022
 */

public class Register extends JFrame {
    public JPanel mainPanel;
    private JButton nextButton;
    private JLabel titleL;
    private JComboBox comboBox1;
    private JLabel forenameL;
    private JTextField forenameT;
    private JLabel surnameL;
    private JTextField surnameT;
    private JTextField mobileT;
    private JLabel mobileL;
    private JTextField addOneT;
    private JLabel addOneL;
    private JLabel addTwoL;
    private JTextField addTwoT;
    private JLabel cityL;
    private JTextField cityT;
    private JLabel postcodeL;
    private JTextField postcodeT;
    private JLabel emailL;
    private JTextField emailT;
    private JLabel passwordL;
    private JLabel confirmPasswordL;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JCheckBox showPassword;
    private final AbstractHomebreaksPanel PANEL;

    public Register(JFrame frame, AbstractHomebreaksPanel panel) {
        this.PANEL = panel;
        nextButton.addActionListener(e -> {
            String email = emailT.getText();
            String password = passwordField.getText();
            String passwordConfirm = confirmPasswordField.getText();
            String title = (String) comboBox1.getSelectedItem();
            String forename = forenameT.getText();
            String surname = surnameT.getText();
            String mobile = mobileT.getText();
            String addressLineOne = addOneT.getText();
            String addressLineTwo = addTwoT.getText();
            String city = cityT.getText();
            String postCode = postcodeT.getText();

            // validation
            if (email.length() <= 100) {
                if (FieldValidator.isEmailValid(email)) { //isEmailValid includes length
                    if (FieldValidator.isPhoneValid(mobile)) { //isPhoneValid includes length
                        if (FieldValidator.isPasswordValid(password)) { //isPassword includes length
                            if (forename.length() <= 25 && surname.length() <= 50) {
                                if (forename.length() != 0 && surname.length() != 0 &&
                                        FieldValidator.isNameValid(forename) &&
                                        FieldValidator.isNameValid(surname)) {
                                    if (addressLineOne.length() <= 100 && addressLineTwo.length() <= 100 &&
                                            city.length() <= 100) {
                                        if (addressLineOne.length() != 0 && city.length() != 0 &&
                                                FieldValidator.isLatinNumSpace(addressLineOne) &&
                                                FieldValidator.isLatinAndSpace(city)) {
                                            if (FieldValidator.isPostZipValid(postCode)) { //isPostZipValid includes length
                                                if (!password.equals(passwordConfirm)) {
                                                    JOptionPane.showOptionDialog(null,
                                                            "Password don't match.",
                                                            "Error Message", JOptionPane.DEFAULT_OPTION,
                                                            JOptionPane.ERROR_MESSAGE, PANEL.imgTransformed, null, null);

                                                } else {
                                                    Person emailChecker = new Person();
                                                    if (!emailChecker.checkEmail(email)) {

                                                        Address userAddress = new Address(addressLineOne, addressLineTwo, city, postCode);
                                                        Person newUser = new Person(email, title, forename, surname, mobile, userAddress, password);

                                                        frame.dispose();
                                                        JFrame nextFrame = new JFrame("Role choose");
                                                        nextFrame.setContentPane(new RoleChoose(nextFrame, newUser).mainPanel);
                                                        nextFrame.pack();
                                                        nextFrame.setLocationRelativeTo(null); // Center the form
                                                        nextFrame.setVisible(true);

                                                    } else {
                                                        JOptionPane.showOptionDialog(null,
                                                                "Account already created for that email",
                                                                "Error Message", JOptionPane.DEFAULT_OPTION,
                                                                JOptionPane.ERROR_MESSAGE, PANEL.imgTransformed, null, null);
                                                    }
                                                }
                                            } else {
                                                JOptionPane.showMessageDialog
                                                        (null,
                                                                "Please enter a valid post/zip code.",
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
                                                        "Address Lines and City can not exceed 100 characters." +
                                                                "\nYour Address Line One: " + addressLineOne.length() + " characters." +
                                                                "\nYour Adresss Line Two: " + addressLineTwo.length() + " characters." +
                                                                "\nYour City: " + city.length() + " characters.",
                                                        "Invalid Entry",
                                                        JOptionPane.ERROR_MESSAGE);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog
                                            (null,
                                                    "Please enter a valid forename and surname.",
                                                    "Invalid Entry",
                                                    JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog
                                        (null,
                                                "Forename can not exceed 25 characters and surname can" +
                                                        "not exceed 50 characters." +
                                                        "\nYour forename: " + forename.length() + " characters." +
                                                        "\nYour surname: " + surname.length() + " characters.",
                                                "Invalid Entry",
                                                JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog
                                    (null,
                                            "Please enter a valid password that meets the following conditions:" +
                                                    "\n- Password must contain at least one digit [0-9]." +
                                                    "\n- Password must contain at least one lowercase Latin character [a-z]." +
                                                    "\n- Password must contain at least one uppercase Latin character [A-Z]." +
                                                    "\n- Password must contain at least one special character like ! @ # & ( )" +
                                                    "\n- Password must contain a length of at least 8 characters and a maximum of 20 characters.",
                                            "Invalid Entry",
                                            JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog
                                (null,
                                        "Please enter a valid phone number." +
                                                "\n Use only numbers and no spaces. Optional + at the start.",
                                        "Invalid Entry",
                                        JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog
                            (null,
                                    "Please enter a valid email address.",
                                    "Invalid Entry",
                                    JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog
                        (null,
                                "Email address can not exceed 100 characters." +
                                        "\nYour email: " + email.length() + " characters.",
                                "Invalid Entry",
                                JOptionPane.ERROR_MESSAGE);
            }
        });

        // Hide/unhide passwords
        showPassword.addActionListener(ex -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
                confirmPasswordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('\u25CF');
                confirmPasswordField.setEchoChar('\u25CF');
            }
        });

    }




    private void createUIComponents() {
        // TODO: place custom component creation code here
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
        mainPanel.setLayout(new GridLayoutManager(11, 6, new Insets(50, 50, 50, 50), -1, -1));
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Mr");
        defaultComboBoxModel1.addElement("Mrs");
        defaultComboBoxModel1.addElement("Ms");
        defaultComboBoxModel1.addElement("Dr");
        comboBox1.setModel(defaultComboBoxModel1);
        mainPanel.add(comboBox1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        forenameL = new JLabel();
        forenameL.setText("Forename");
        mainPanel.add(forenameL, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        forenameT = new JTextField();
        forenameT.setText("");
        mainPanel.add(forenameT, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        surnameL = new JLabel();
        surnameL.setText("Surname");
        mainPanel.add(surnameL, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        surnameT = new JTextField();
        mainPanel.add(surnameT, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        mobileL = new JLabel();
        mobileL.setText("Mobile");
        mainPanel.add(mobileL, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mobileT = new JTextField();
        mainPanel.add(mobileT, new GridConstraints(2, 2, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addOneL = new JLabel();
        addOneL.setText("Address Line 1");
        mainPanel.add(addOneL, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addOneT = new JTextField();
        mainPanel.add(addOneT, new GridConstraints(3, 2, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addTwoL = new JLabel();
        addTwoL.setText("Address Line 2");
        mainPanel.add(addTwoL, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addTwoT = new JTextField();
        mainPanel.add(addTwoT, new GridConstraints(4, 2, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cityL = new JLabel();
        cityL.setText("City");
        mainPanel.add(cityL, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cityT = new JTextField();
        mainPanel.add(cityT, new GridConstraints(5, 2, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        postcodeL = new JLabel();
        postcodeL.setText("Post Code");
        mainPanel.add(postcodeL, new GridConstraints(6, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        postcodeT = new JTextField();
        mainPanel.add(postcodeT, new GridConstraints(6, 2, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        titleL = new JLabel();
        titleL.setText("Title");
        mainPanel.add(titleL, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        emailL = new JLabel();
        emailL.setText("Email");
        mainPanel.add(emailL, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        emailT = new JTextField();
        mainPanel.add(emailT, new GridConstraints(1, 2, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nextButton = new JButton();
        Font nextButtonFont = this.$$$getFont$$$(null, -1, 20, nextButton.getFont());
        if (nextButtonFont != null) nextButton.setFont(nextButtonFont);
        nextButton.setText("Next step");
        mainPanel.add(nextButton, new GridConstraints(10, 2, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordL = new JLabel();
        passwordL.setText("Password");
        mainPanel.add(passwordL, new GridConstraints(7, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        confirmPasswordL = new JLabel();
        confirmPasswordL.setText("Confirm Password");
        mainPanel.add(confirmPasswordL, new GridConstraints(8, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordField = new JPasswordField();
        mainPanel.add(passwordField, new GridConstraints(8, 2, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        confirmPasswordField = new JPasswordField();
        mainPanel.add(confirmPasswordField, new GridConstraints(7, 2, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        showPassword = new JCheckBox();
        showPassword.setText("Show passwords");
        mainPanel.add(showPassword, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
