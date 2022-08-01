package com.homebreaks.main;

/**
 * Represents Living space facility
 *
 * @version 1.0
 *
 * @author team055
 */

public class Living {
    private int hasWLAN;
    private int hasTV;
    private int hasSatellite;
    private int hasStreaming;
    private int hasDVD;
    private int hasBoardGames;

    public Living () {}

    public Living(int hasWLAN, int hasTV, int hasSatellite, int hasStreaming, int hasDVD, int hasBoardGames) {
        this.hasWLAN = hasWLAN;
        this.hasTV = hasTV;
        this.hasSatellite = hasSatellite;
        this.hasStreaming = hasStreaming;
        this.hasDVD = hasDVD;
        this.hasBoardGames = hasBoardGames;
    }

    // Getters and setters
    public void setHasWLAN(int hasWLAN) {this.hasWLAN = hasWLAN;}
    public int getHasWLAN() {return this.hasWLAN;}

    public void setHasTV(int hasTV) {this.hasTV = hasTV;}
    public int getHasTV() {return this.hasTV;}

    public void setHasSatellite(int hasSatellite) {this.hasSatellite = hasSatellite;}
    public int getHasSatellite() {return this.hasSatellite;}

    public void setHasStreaming(int hasStreaming) {this.hasStreaming = hasStreaming;}
    public int getHasStreaming() {return this.hasStreaming;}

    public void setHasDVD(int hasDVD) {this.hasDVD = hasDVD;}
    public int getHasDVD() {return this.hasDVD;}

    public void setHasBoardGames(int hasBoardGames) {this.hasBoardGames = hasBoardGames;}
    public int getHasBoardGames() {return this.hasBoardGames;}

    @Override
    public String toString() {
        return "Living{" +
                "hasWLAN=" + hasWLAN +
                ", hasTV=" + hasTV +
                ", hasSatellite=" + hasSatellite +
                ", hasStreaming=" + hasStreaming +
                ", hasDVD=" + hasDVD +
                ", hasBoardGames=" + hasBoardGames +
                '}';
    }
}
