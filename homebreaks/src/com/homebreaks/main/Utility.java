package com.homebreaks.main;

public class Utility {
    private int hasHeating;
    private int hasWasher;
    private int hasClothesDryer;
    private int hasExtinguisher;
    private int hasSmokeAlarm;
    private int hasFirstAid;

    /**
     * Represents Utility facility
     *
     * @version 1.0
     *
     * @author team055
     *
     */

    public Utility () {}

    public Utility
            (int hasHeating, int hasWasher, int hasClothesDryer, int hasExtinguisher, int hasSmokeAlarm,
             int hasFirstAid) {
        this.hasHeating = hasHeating;
        this.hasWasher = hasWasher;
        this.hasClothesDryer = hasClothesDryer;
        this.hasExtinguisher = hasExtinguisher;
        this.hasSmokeAlarm = hasSmokeAlarm;
        this.hasFirstAid = hasFirstAid;
    }

    // Getters and setters
    public void setHasHeating(int hasHeating) {this.hasHeating = hasHeating;}
    public int getHasHeating() {return this.hasHeating;}

    public void setHasWasher(int hasWasher) {this.hasWasher = hasWasher;}
    public int getHasWasher() {return this.hasWasher;}

    public void setHasClothesDryer(int hasClothesDryer) {this.hasClothesDryer = hasClothesDryer;}
    public int getHasClothesDryer() {return this.hasClothesDryer;}

    public void setHasExtinguisher(int hasExtinguisher) {this.hasExtinguisher = hasExtinguisher;}
    public int getHasExtinguisher() {return this.hasExtinguisher;}

    public void setHasSmokeAlarm(int hasSmokeAlarm) {this.hasSmokeAlarm = hasSmokeAlarm;}
    public int getHasSmokeAlarm() {return this.hasSmokeAlarm;}

    public void setHasFirstAid(int hasFirstAid) {this.hasFirstAid = hasFirstAid;}
    public int getHasFirstAid() {return this.hasFirstAid;}

    @Override
    public String toString() {
        return "Utility{" +
                "hasHeating=" + hasHeating +
                ", hasWasher=" + hasWasher +
                ", hasClothesDryer=" + hasClothesDryer +
                ", hasExtinguisher=" + hasExtinguisher +
                ", hasSmokeAlarm=" + hasSmokeAlarm +
                ", hasFirstAid=" + hasFirstAid +
                '}';
    }
}
