package org.dip.tus.entity;

import org.dip.tus.booking.AbstractBooking;
import org.dip.tus.booking.RoomBooking;

public class Room extends AbstractEntity<RoomBooking>  {
    @Override
    public boolean isOccupied() {
        return false;
    }

    @Override
    public boolean doesBookingClash(RoomBooking booking) {
        return false;
    }

    @Override
    public RoomBooking getNextBooking() {
        return null;
    }
}
