package org.dip.tus.entity;

import org.dip.tus.booking.AbstractBooking;

import java.util.PriorityQueue;

public abstract class AbstractEntity<T extends AbstractBooking> {
    private PriorityQueue<AbstractBooking> bookings;


    public abstract boolean isOccupied();
    public abstract boolean doesBookingClash(T booking);
    public abstract AbstractBooking getNextBooking();
}
