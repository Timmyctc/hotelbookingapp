package org.dip.tus.parking;

import org.dip.tus.core.AbstractEntity;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class ParkingSpot extends AbstractEntity<ParkingBooking> {

    private char section;
    private int spotNumber;

    public ParkingSpot(char section, int spotNumber) {
        if(section >= 'A' && section <= 'F') {this.section = section;}
        else {throw new IllegalArgumentException("Invalid Section");}
        if(spotNumber >= 1 && spotNumber <= 20) {this.spotNumber = spotNumber;}
        else throw new IllegalArgumentException("Invalid Parking Spot Number");
        this.bookings = new PriorityQueue<>(Comparator.comparing(ParkingBooking::getBookingDateTimeStart));
    }

    @Override
    public String getId() {
        return "";
    }

    /**
     * Streams through the bookings PriorityQueue and checks for any match where the new prospective booking
     * may overlap with any existing bookings.
     *
     * @param newBooking - Prospective booking to be checked against existing booking Queue
     * @return boolean if there is a clash between newBooking and the existing booking Queue
     */
    @Override
    public boolean doesBookingClash(ParkingBooking newBooking) {
        if(bookings.isEmpty()) {return false;}
        return bookings
                .stream()
                .anyMatch(b -> newBooking.getBookingDateTimeStart().isBefore(b.getBookingDateTimeEnd()) &&
                newBooking.getBookingDateTimeEnd().isAfter(b.getBookingDateTimeStart()));
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "section=" + section +
                ", spotNumber=" + spotNumber +
                ", totalBookings=" + bookings.size() +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot that = (ParkingSpot) o;
        return section == that.section && spotNumber == that.spotNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(section, spotNumber);
    }
}
