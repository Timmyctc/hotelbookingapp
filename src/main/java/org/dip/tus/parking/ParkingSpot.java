package org.dip.tus.parking;

public class ParkingSpot {

    private char section;
    private int spotNumber;
    private boolean isOccupied;

    public ParkingSpot(char section, int spotNumber) {
        if(section >= 65 && section <= 70) {
            this.section = section;
        }
        else {
            throw new IllegalArgumentException("Invalid Section");
        }
        if(spotNumber <= 1 || spotNumber >= 53) {
            this.spotNumber = spotNumber;
        }
        else throw new IllegalArgumentException("Invalid Parking Spot Number");
    }

    public boolean isSpotOccupied() {
        return isOccupied;
    }
}
