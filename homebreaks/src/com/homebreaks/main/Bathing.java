package com.homebreaks.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Represents bathing facility
 *
 * @version 1.0
 *
 * @author team055
 *
 */

public class Bathing {
    private int hasHairDryer;
    private int hasShampoo;
    private int hasToiletPaper;
    private List<Bathroom> bathrooms = new ArrayList<>();

    // Constructor
    public Bathing
            (int hasHairDryer, int hasShampoo, int hasToiletPaper) {
        this.hasHairDryer = hasHairDryer;
        this.hasShampoo = hasShampoo;
        this.hasToiletPaper = hasToiletPaper;
    }

    // Getters and setters
    public void setHasHairDryer(int hasHairDryer) {this.hasHairDryer = hasHairDryer;}
    public int getHasHairDryer() {return hasHairDryer;}

    public void setHasShampoo(int hasShampoo) {this.hasShampoo = hasShampoo;}
    public int getHasShampoo() {return hasShampoo;}

    public void setHasToiletPaper(int hasToiletPaper) {this.hasToiletPaper = hasToiletPaper;}
    public int getHasToiletPaper() {return hasToiletPaper;}

    public void setBathrooms(List<Bathroom> bathrooms) {this.bathrooms = bathrooms;}
    public List<Bathroom> getBathrooms() {return bathrooms;}

    /**
     * Add a bathroom to the list of bathrooms
     *
     * @param bathroom bathroom to add to bathroom list
     */
    public void addBathroom(Bathroom bathroom) {bathrooms.add(bathroom);}

    /**
     * Returns the number of bathrooms associated with the property
     *
     * @return size of the bathroom array
     */
    public int getBathroomCount()  {return bathrooms.size();}

    @Override
    public String toString() {
        return "Bathing{" +
                "hasHairDryer=" + hasHairDryer +
                ", hasShampoo=" + hasShampoo +
                ", hasToiletPaper=" + hasToiletPaper +
                ", bathrooms=" + bathrooms +
                '}';
    }
}
