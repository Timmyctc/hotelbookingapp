package org.dip.tus.parking;

import org.dip.tus.core.AbstractEntity;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * Represents a parking spot in a parking lot. Each spot belongs to a specific section and has a unique spot number.
 * Parking spots track bookings and calculate costs based on booking duration.
 */
public class ParkingSpot extends AbstractEntity<ParkingBooking> {

    private char section;
    private int spotNumber;
    private final double costPerHour = 5.0;
    private final double costPerDay = 25;

    /**
     * Constructs a parking spot with a specified section and spot number.
     *
     * @param section    The section of the parking spot (A-D).
     * @param spotNumber The number of the parking spot (1-5).
     * @throws IllegalArgumentException If the section or spot number is invalid.
     */
    public ParkingSpot(char section, int spotNumber) {
        if (section >= 'A' && section <= 'D') {
            this.section = section;
        } else {
            throw new IllegalArgumentException("Invalid Section");
        }
        if (spotNumber >= 1 && spotNumber <= 5) {
            this.spotNumber = spotNumber;
        } else {
            throw new IllegalArgumentException("Invalid Parking Spot Number");
        }
        this.bookings = new PriorityQueue<>(Comparator.comparing(ParkingBooking::getBookingDateTimeStart));
    }

    /**
     * Calculates the cost of parking for the given booking period.
     * Charges €5 per hour, capped at €25 per day.
     *
     * @param start The start time of the booking.
     * @param end   The end time of the booking.
     * @return The calculated cost based on the booking duration.
     */
    public double calculateCost(LocalDateTime start, LocalDateTime end) {
        long hours = java.time.Duration.between(start, end).toHours();
        if (hours >= 5) {
            return costPerDay * (hours / 24);
        }
        return costPerHour * hours;
    }

    @Override
    public String getId() {
        return section + Integer.toString(spotNumber);
    }

    /**
     * Checks if the new booking clashes with any existing bookings.
     *
     * @param newBooking The new booking to check for clashes.
     * @return {@code true} if there is a clash; {@code false} otherwise.
     */
    @Override
    public boolean doesBookingClash(ParkingBooking newBooking) {
        if (bookings.isEmpty()) {
            return false;
        }
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

    public char getSection() {
        return this.section;
    }

    public int getSpotNumber() {
        return this.spotNumber;
    }
}
