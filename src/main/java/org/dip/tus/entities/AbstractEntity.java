package org.dip.tus.entities;

import org.dip.tus.booking.AbstractBooking;
import org.dip.tus.booking.ParkingBooking;

import java.util.PriorityQueue;

public abstract class AbstractEntity<T extends AbstractBooking> {
    private PriorityQueue<AbstractBooking> bookings;


    public abstract boolean isOccupied();
    public abstract boolean doesBookingClash(T booking);
    public abstract AbstractBooking getNextBooking();
}
