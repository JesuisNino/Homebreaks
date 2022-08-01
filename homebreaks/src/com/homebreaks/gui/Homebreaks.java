package com.homebreaks.gui;

import javax.swing.*;

public class Homebreaks extends JFrame {
    public Homebreaks (AbstractHomebreaksPanel panel) {
        setTitle("Homebreaks: Find your perfect holiday home!");

        add(panel);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
