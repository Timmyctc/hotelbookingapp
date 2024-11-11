package org.dip.tus.entity;

import org.dip.tus.booking.AbstractBooking;
import org.dip.tus.booking.RestaurantBooking;

public class Table extends AbstractEntity<RestaurantBooking> {

    @Override
    public boolean isOccupied() {
        return false;
    }

    @Override
    public boolean doesBookingClash(RestaurantBooking booking) {
        return false;
    }

    @Override
    public RestaurantBooking getNextBooking() {
        return null;
    }
}
