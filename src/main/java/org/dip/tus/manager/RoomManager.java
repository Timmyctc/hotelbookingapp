package org.dip.tus.manager;

import org.dip.tus.booking.RoomBooking;

public class RoomManager implements BookingManager<RoomBooking> {
    @Override
    public boolean addBooking(RoomBooking booking) {
        return false;
    }

    @Override
    public boolean removeBooking(RoomBooking booking) {
        return false;
    }
}
