package com.homebreaks.main;

/**
 * Represents Outdoors space facility
 *
 * @version 1.0
 *
 * @author team055
 */

public class Outdoors {
    private int hasOnSiteParking;
    private int hasRoadParking;
    private int hasCarPark;
    private int hasPatio;
    private int hasBBQ;

    public Outdoors () {}

    public Outdoors
            (int hasOnSiteParking, int hasRoadParking, int hasCarPark, int hasPatio, int hasBBQ) {
        this.hasOnSiteParking = hasOnSiteParking;
        this.hasRoadParking = hasRoadParking;
        this.hasCarPark = hasCarPark;
        this.hasPatio = hasPatio;
        this.hasBBQ = hasBBQ;
    }

    // Getters and setters
    public void setHasOnSiteParking(int hasOnSiteParking) {this.hasOnSiteParking = hasOnSiteParking;}
    public int getHasOnSiteParking() {return this.hasOnSiteParking;}

    public void setHasRoadParking(int hasRoadParking) {this.hasRoadParking = hasRoadParking;}
    public int getHasRoadParking() {return this.hasRoadParking;}

    public void setHasCarPark(int hasCarPark) {this.hasCarPark = hasCarPark;}
    public int getHasCarPark() {return this.hasCarPark;}

    public void setHasPatio(int hasPatio) {this.hasPatio = hasPatio;}
    public int getHasPatio() {return this.hasPatio;}

    public void setHasBBQ(int hasBBQ) {this.hasBBQ = hasBBQ;}
    public int getHasBBQ() {return this.hasBBQ;}

    @Override
    public String toString() {
        return "Outdoors{" +
                "hasOnSiteParking=" + hasOnSiteParking +
                ", hasRoadParking=" + hasRoadParking +
                ", hasCarPark=" + hasCarPark +
                ", hasPatio=" + hasPatio +
                ", hasBBQ=" + hasBBQ +
                '}';
    }
}
