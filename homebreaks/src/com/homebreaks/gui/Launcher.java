package com.homebreaks.gui;

import com.homebreaks.main.Person;

/**
 * Class to launch main application and entry point into Homebreaks app.
 *
 * @version 1.0
 *
 * @author team055
 *
 */

public class Launcher {
    private Person user;

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.startGui();
    }

    public Launcher() {
        user = new Person ("email",false, false);
    }

    /**
     * Start the graphical user interface.
     */
    public void startGui() {
        AbstractHomebreaksPanel browserPanel = new HomebreaksPanel(user);
        Homebreaks homebreaks = new Homebreaks(browserPanel);
    }
}
