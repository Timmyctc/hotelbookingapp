package org.dip.tus.entity;

import org.dip.tus.booking.ParkingBooking;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class ParkingSpot extends AbstractEntity<ParkingBooking> {

    private char section;
    private int spotNumber;
    private PriorityQueue<ParkingBooking> bookings;

    public ParkingSpot(char section, int spotNumber) {
        if(section >= 65 && section <= 70) {this.section = section;}
        else {throw new IllegalArgumentException("Invalid Section");}
        if(spotNumber <= 1 || spotNumber >= 20) {this.spotNumber = spotNumber;}
        else throw new IllegalArgumentException("Invalid Parking Spot Number");
        this.bookings = new PriorityQueue<>(Comparator.comparing(ParkingBooking::getBookingDateTimeStart));
    }

    /**
     * Retrieves the next booking in the PriorityQueue and checks for a clash between its
     * start & end times, and the current time (LocalDateTime.now())
     *
     * @return boolean on whether the spot is currently booked based on the above criteria.
     */
    @Override
    public boolean isOccupied() {
        ParkingBooking nextBooking = getNextBooking();
        if(Objects.nonNull(nextBooking)) {
            return LocalDateTime.now().isAfter(nextBooking.getBookingDateTimeStart())
                    && LocalDateTime.now().isBefore(nextBooking.getBookingDateTimeEnd());
        }
        return false;
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
        return bookings
                .stream()
                .anyMatch(b -> newBooking.getBookingDateTimeStart().isBefore(b.getBookingDateTimeEnd()) &&
                newBooking.getBookingDateTimeEnd().isAfter(b.getBookingDateTimeStart()));
    }

    /**
     * Adds new booking to bookings PriorityQueue, because we've defined the Comparator
     * in the ParkingSpot constructor it will automatically use this as comparison when adding new bookings
     *
     * @param newBooking newBooking to add to bookings Queue.
     * @return true on successfully adding booking, false on a clash
     */
    public boolean addBookingToQueue(ParkingBooking newBooking) {
        if(!doesBookingClash(newBooking)) {
            bookings.add(newBooking);
            return true;
        }
        return false;
    }
}
