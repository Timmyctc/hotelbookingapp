package org.dip.tus.parking;

import org.dip.tus.booking.ParkingBooking;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class ParkingSpot {

    private char section;
    private int spotNumber;
    private boolean isOccupied;
    private PriorityQueue<ParkingBooking> bookings;

    public ParkingSpot(char section, int spotNumber) {
        if(section >= 65 && section <= 70) {this.section = section;}
        else {throw new IllegalArgumentException("Invalid Section");}
        if(spotNumber <= 1 || spotNumber >= 20) {this.spotNumber = spotNumber;}
        else throw new IllegalArgumentException("Invalid Parking Spot Number");
        this.bookings = new PriorityQueue<>(Comparator.comparing(ParkingBooking::getBookingDateTimeStart);
    }

    public ParkingBooking getNextBooking() {
        return bookings.peek();
    }

    public boolean isSpotOccupied() {
        ParkingBooking nextBooking = getNextBooking();
        if(Objects.nonNull(nextBooking)) {
            return LocalDateTime.now().isAfter(nextBooking.getBookingDateTimeStart())
                    && LocalDateTime.now().isBefore(nextBooking.getBookingDateTimeEnd());
        }
        return false;
    }

    public void setSpotOccupiedStatus(boolean isSpotOccupied) {
        this.isOccupied = isSpotOccupied;
    }
}
