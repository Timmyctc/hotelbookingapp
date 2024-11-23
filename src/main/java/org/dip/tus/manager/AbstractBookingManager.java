package org.dip.tus.manager;

import org.dip.tus.booking.AbstractBooking;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBookingManager <T extends AbstractBooking> implements BookingManager {

    protected List<AbstractBooking> bookings = new ArrayList<AbstractBooking>();

    @Override
    public boolean addBooking(AbstractBooking booking) {
        if (!doesBookingClash(booking)) {
            bookings.add(booking);
            return true;
        }
        return false;
    }

    public boolean removeBooking(AbstractBooking booking) {
        return bookings.remove(booking);
    }

    public abstract boolean doesBookingClash(AbstractBooking booking);

    @Override
    public T getNextBooking() {
        return null;
    }
}
