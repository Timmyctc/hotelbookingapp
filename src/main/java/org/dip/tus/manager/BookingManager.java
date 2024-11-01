package org.dip.tus.manager;

public interface BookingManager <T> {
    boolean addBooking(T booking);
    boolean removeBooking(T booking);

}
