package com.homebreaks.main;

/**
 * Represents Kitchen facility
 *
 * @version 1.0
 *
 * @author team055
 */

public class Kitchen {
    private int hasFridge;
    private int hasMicro;
    private int hasOven;
    private int hasStove;
    private int hasDishwasher;
    private int hasTableware;
    private int hasCookware;
    private int hasBasics;

    public Kitchen () {}

    public Kitchen
            (int hasFridge, int hasMicro, int hasOven,
             int hasStove, int hasDishwasher, int hasTableware, int hasCookware,
             int hasBasics) {
        this.hasFridge = hasFridge;
        this.hasMicro = hasMicro;
        this.hasOven = hasOven;
        this.hasStove = hasStove;
        this.hasDishwasher = hasDishwasher;
        this.hasTableware = hasTableware;
        this.hasCookware = hasCookware;
        this.hasBasics = hasBasics;
    }

    // Getters and setters
    public void setHasFridge(int hasFridge) {this.hasFridge = hasFridge;}
    public int getHasFridge() {return this.hasFridge;}

    public void setHasMicro(int hasMicro) {this.hasMicro = hasMicro;}
    public int getHasMicro() {return this.hasMicro;}

    public void setHasOven(int hasOven) {this.hasOven = hasOven;}
    public int getHasOven() {return this.hasOven;}

    public void setHasStove(int hasStove) {this.hasStove = hasStove;}
    public int getHasStove() {return this.hasStove;}

    public void setHasDishwasher(int hasDishwasher) {this.hasDishwasher = hasDishwasher;}
    public int getHasDishwasher() {return this.hasDishwasher;}

    public void setHasTableware(int hasTableware) {this.hasTableware = hasTableware;}
    public int getHasTableware() {return this.hasTableware;}

    public void setHasCookware(int hasCookware) {this.hasCookware = hasCookware;}
    public int getHasCookware() {return this.hasCookware;}

    public void setHasBasics(int hasBasics) {this.hasBasics = hasBasics;}
    public int getHasBasics() {return this.hasBasics;}

    @Override
    public String toString() {
        return "Kitchen{" +
                "hasFridge=" + hasFridge +
                ", hasMicro=" + hasMicro +
                ", hasOven=" + hasOven +
                ", hasStove=" + hasStove +
                ", hasDishwasher=" + hasDishwasher +
                ", hasTableware=" + hasTableware +
                ", hasCookware=" + hasCookware +
                ", hasBasics=" + hasBasics +
                '}';
    }
}
