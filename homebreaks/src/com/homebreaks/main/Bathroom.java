package com.homebreaks.main;

/**
 * Represents Bathroom
 *
 * @version 1.0
 *
 * @author team055
 *
 */

public class Bathroom {
    private int hasToilet;
    private int hasBath;
    private int hasShower;
    private int isShared;

    public Bathroom () {}

    public Bathroom
            (int hasToilet, int hasBath, int hasShower, int isShared) {
        this.hasToilet = hasToilet;
        this.hasBath = hasBath;
        this.hasShower = hasShower;
        this.isShared = isShared;
    }

    // Getters and setters
    public void setHasToilet(int hasToilet) {this.hasToilet = hasToilet;}
    public int getHasToilet() {return hasToilet;}

    public void setHasBath(int hasBath) {this.hasBath = hasBath;}
    public int getHasBath() {return hasBath;}

    public void setHasShower(int hasShower) {this.hasShower = hasShower;}
    public int getHasShower() {return hasShower;}

    public void setShared(int shared) {isShared = shared;}
    public int getShared() {return isShared;}

    @Override
    public String toString() {
        return "Bathroom{" +
                "hasToilet=" + hasToilet +
                ", hasBath=" + hasBath +
                ", hasShower=" + hasShower +
                ", isShared=" + isShared +
                '}';
    }
}
