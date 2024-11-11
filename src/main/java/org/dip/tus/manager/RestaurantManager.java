package org.dip.tus.manager;

import org.dip.tus.booking.TableBooking;
import org.dip.tus.entity.Table;

public class RestaurantManager implements BookingManager<TableBooking> {
    @Override
    public boolean addBooking(TableBooking booking) {
        return false;
    }

    @Override
    public boolean removeBooking(TableBooking booking) {
        return false;
    }
}
