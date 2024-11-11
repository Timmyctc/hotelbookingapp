package org.dip.tus.booking;

import org.dip.tus.Customer;

import java.time.LocalDateTime;

public class TableBooking extends AbstractBooking {

    public TableBooking(Customer customer, int roomNumber, LocalDateTime bookingDateTimeStart) {
        super(customer, roomNumber, bookingDateTimeStart);
    }

    @Override
    public String generateBookingID() {
        return "";
    }
}
