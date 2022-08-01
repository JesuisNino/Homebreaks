package com.homebreaks.main;

/**
 * Represents Bedroom
 *
 * @version 1.0
 *
 * @author team055
 *
 */

public class Bedroom {
    private int bed1;
    private int bed2;

    public Bedroom () {}

    public Bedroom
            (int bed1, int bed2) {
        this.bed1 = bed1;
        this.bed2 = bed2;
    }

    // setters and getters
    public void setBed1(int bed1) {this.bed1 = bed1;}
    public int getBed1() {return bed1;}

    public void setBed2(int bed2) {this.bed2 = bed2;}
    public int getBed2() {return bed2;}

    /**
     * Gets the number of beds in the bedroom
     *
     * @return number of beds in bedroom
     */
    public int getBedCount() {
        if (bed1 == 1 ^ bed2 == 1) { return 1; }
        else return 2;
    }

    /**
     * Calculates the number of guests who can sleep in the bedroom
     *
     * @return number of guests who can stay in a bedroom
     */
    public int calculateNumberGuests() {
        int numberGuests = 0;
        if (bed1 == 1) {
            numberGuests += 1;
        } else {
            numberGuests += 2;
        }
        if (bed2 == 0) {
        } else if (bed2 == 1) {
            numberGuests += 1;
        } else {
            numberGuests += 2;
        }
        return numberGuests;
    }

    @Override
    public String toString() {
        return "Bedroom{" +
                "bed1=" + bed1 +
                ", bed2=" + bed2 +
                '}';
    }
}
