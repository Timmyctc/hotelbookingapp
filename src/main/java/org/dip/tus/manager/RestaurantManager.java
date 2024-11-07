package org.dip.tus.manager;

import org.dip.tus.entity.Table;

public class RestaurantManager implements BookingManager<Table> {
    @Override
    public boolean addBooking(Table booking) {
        return false;
    }

    @Override
    public boolean removeBooking(Table booking) {
        return false;
    }
}
