package org.dip.tus.core;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public abstract class AbstractEntity<T extends AbstractBooking> {

    protected PriorityQueue<T> bookings;

    public abstract String getId();

    public boolean doesBookingClash(T booking) {
        if(bookings.isEmpty()) {return false;}
        return bookings
                .stream()
                .anyMatch(b -> booking.getBookingDateTimeStart().isBefore(b.getBookingDateTimeEnd()) &&
                        booking.getBookingDateTimeEnd().isAfter(b.getBookingDateTimeStart()));
    }

    /**
     * Adds new booking to bookings PriorityQueue, because we've defined the Comparator
     * in the ParkingSpot constructor it will automatically use this as comparison when adding new bookings
     *
     * @param newBooking newBooking to add to bookings Queue.
     * @return true on successfully adding booking, false on a clash
     */
    public boolean addBookingToQueue(T newBooking) {
        if(!doesBookingClash(newBooking)) {
            bookings.add(newBooking);
            return true;
        }
        return false;
    }

    /**
     * Removes booking from PriorityQueue if it exists
     *
     * @param booking to remove from bookings Queue.
     * @return true on successfully removing booking, false if doesnt exist
     */
    public boolean removeBookingFromQueue(T booking) {
        return bookings.remove(booking);
    }

    /**
     * Retrieves the next booking in the PriorityQueue and checks for a clash between its
     * start & end times, and the current time (LocalDateTime.now())
     *
     * @return boolean on whether the spot is currently booked based on the above criteria.
     */
    public boolean isOccupied(LocalDateTime currentTime) {
        T nextBooking = getNextBooking();
        if (Objects.nonNull(nextBooking)) {
            return currentTime.isAfter(nextBooking.getBookingDateTimeStart())
                    && currentTime.isBefore(nextBooking.getBookingDateTimeEnd());
        }
        return false;
    }

    /**
     * @return next booking in the PriorityQueue, as each booking added to the queue is compared based on its startTime
     * we can be sure next booking will be the next chronologically.
     */
    public T getNextBooking() {

        return bookings.peek();
    }

    public PriorityQueue<T> getAllBookings() {
        return new PriorityQueue<T>(bookings);
    }
}
