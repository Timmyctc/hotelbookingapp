package org.dip.tus.booking;

import org.dip.tus.Customer;

import java.time.LocalDateTime;

public abstract class AbstractBooking {

    private int roomNumber;
    private Customer customer;
    private LocalDateTime bookingDateTimeStart;
    private String bookingID;

    public AbstractBooking(Customer customer, int roomNumber, LocalDateTime bookingDateTimeStart) {
        this.customer = customer;
        this.roomNumber = roomNumber;
        this.bookingDateTimeStart = bookingDateTimeStart;
//        this.bookingID = "B" + String.valueOf(Math.abs(customer.hashCode() + roomNumber + bookingDateTimeStart.hashCode()));
//        System.out.println(bookingID);
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
}
