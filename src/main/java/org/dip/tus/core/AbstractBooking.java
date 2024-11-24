package org.dip.tus.core;

import org.dip.tus.customer.Customer;
import org.dip.tus.exception.BookingDateArgumentException;

import java.time.LocalDateTime;

public abstract class AbstractBooking {

//    private final String bookingId;
    private int roomNumber;
    private Customer customer;
    private LocalDateTime bookingDateTimeStart;
    private LocalDateTime bookingDateTimeEnd;

    public AbstractBooking(Customer customer, int roomNumber, LocalDateTime bookingDateTimeStart,
                           LocalDateTime bookingDateTimeEnd) throws BookingDateArgumentException {
        if(!bookingDateTimeStart.isBefore(bookingDateTimeEnd)) {
            throw new BookingDateArgumentException("bookingDateTimeStart must be before bookingDateTimeEnd");
        }
        this.customer = customer;
        this.roomNumber = roomNumber;
        this.bookingDateTimeStart = bookingDateTimeStart;
        this.bookingDateTimeEnd = bookingDateTimeEnd;
    }

    public Customer getCustomer() {
        return customer;
    }
    public int getRoomNumber() {
        return roomNumber;
    }

    public LocalDateTime getBookingDateTimeStart() {
        return bookingDateTimeStart;
    }
    public LocalDateTime getBookingDateTimeEnd() {
        return bookingDateTimeEnd;
    }
    public abstract String generateBookingID();
}
