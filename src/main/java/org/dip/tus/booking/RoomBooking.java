package org.dip.tus.booking;

import org.dip.tus.Customer;

import java.time.LocalDateTime;

public class RoomBooking extends AbstractBooking {

    private LocalDateTime bookingDateTimeEnd;

    public RoomBooking(Customer customer, int roomNumber, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(customer,roomNumber,startDateTime);
        this.bookingDateTimeEnd = endDateTime;
    }

}
