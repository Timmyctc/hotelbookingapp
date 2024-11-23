package org.dip.tus.manager;

import org.dip.tus.booking.AbstractBooking;

public interface BookingManager <T extends AbstractBooking> {
    boolean addBooking(T booking);
    boolean removeBooking(T booking);
    T getNextBooking();

}
