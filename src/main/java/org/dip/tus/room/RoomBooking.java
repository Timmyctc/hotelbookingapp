package org.dip.tus.room;

import org.dip.tus.customer.Customer;
import org.dip.tus.core.AbstractBooking;
import org.dip.tus.exception.BookingDateArgumentException;

import java.time.LocalDateTime;

public final class RoomBooking extends AbstractBooking {

    public RoomBooking(Customer customer, int roomNumber, LocalDateTime startDateTime, LocalDateTime endDateTime) throws BookingDateArgumentException {
        super(customer, startDateTime, endDateTime);
    }

    @Override
    public String generateBookingID() {
        return new StringBuilder()
                .append("P")
                .append(getCustomer().hashCode() + getRoomNumber() + getBookingDateTimeStart().hashCode())
                .toString();
    }

    @Override
    public String toString() {
        return "RoomBooking{" +
                "roomNumber=" + getRoomNumber() +
                ", startDateTime=" + getBookingDateTimeStart() +
                ", endDateTime=" + getBookingDateTimeEnd() +
                ", customer=" + getCustomer().getName() +
//                ", bookingId=" + getBookingId() +
                '}';
    }
}
