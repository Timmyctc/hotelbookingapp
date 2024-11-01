package org.dip.tus.booking;

import org.dip.tus.Customer;

import java.time.LocalDateTime;

public abstract class AbstractBooking {

    private int roomNumber;
    private Customer customer;
    private LocalDateTime bookingDateTimeStart;
    private String bookingID;

    public AbstractBooking(Customer customer, int roomNumber, LocalDateTime bookingDateTimeStart, String bookingID) {
        this.customer = customer;
        this.roomNumber = roomNumber;
        this.bookingDateTimeStart = bookingDateTimeStart;
    }

    public Customer getCustomer() {
        return customer;
    }
    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    public String getBookingID() {
        return bookingID;
    }

    public LocalDateTime getBookingDateTimeStart() {
        return bookingDateTimeStart;
    }

    public abstract String generateBookingID();
}
