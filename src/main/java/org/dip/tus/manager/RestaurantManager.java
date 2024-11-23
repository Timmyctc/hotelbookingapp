package org.dip.tus.manager;

import org.dip.tus.booking.RestaurantBooking;

public class RestaurantManager implements BookingManager<RestaurantBooking> {
    @Override
    public boolean addBooking(RestaurantBooking booking) {
        return false;
    }

    @Override
    public boolean removeBooking(RestaurantBooking booking) {
        return false;
    }

    @Override
    public RestaurantBooking getNextBooking() {
        return null;
    }
}
