package org.dip.tus.manager;

import org.dip.tus.booking.RoomBooking;

public class RoomManager implements BookingManager<RoomBooking> {

    public boolean addBooking(RoomBooking booking) {
        return false;
    }
    public boolean removeBooking(RoomBooking booking) {
        return false;
    }

    @Override
    public RoomBooking getNextBooking() {
        return null;
    }
}
