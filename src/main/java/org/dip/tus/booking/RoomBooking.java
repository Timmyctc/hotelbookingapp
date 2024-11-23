package org.dip.tus.booking;

import org.dip.tus.Customer;
import org.dip.tus.entity.Room;
import org.dip.tus.manager.RoomManager;

import java.time.LocalDateTime;

public final class RoomBooking extends AbstractBooking {

    private LocalDateTime bookingDateTimeEnd;
    private RoomManager roomManager;

    public RoomBooking(Customer customer, int roomNumber, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(customer,roomNumber,startDateTime);
        this.bookingDateTimeEnd = endDateTime;
    }

    @Override
    public String generateBookingID() {
        return "";
    }

    @Override
    public boolean doesBookingClash() {
        return false;
    }
}
