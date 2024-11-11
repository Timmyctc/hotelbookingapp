package org.dip.tus.entity;

import org.dip.tus.booking.AbstractBooking;
import org.dip.tus.booking.ParkingBooking;

import java.util.PriorityQueue;

public abstract class AbstractEntity<T extends AbstractBooking> {
    private PriorityQueue<T> bookings;
    public abstract boolean isOccupied();


    public abstract boolean doesBookingClash(T booking);

    /**
     * @return next booking in the PriorityQueue, as each booking added to the queue is compared based on its startTime
     * we can be sure next booking will be the next chronologically.
     */

    public T getNextBooking() {
        return bookings.peek();
    }
}
