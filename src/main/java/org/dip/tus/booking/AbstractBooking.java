package org.dip.tus.booking;

import org.dip.tus.Customer;

import java.time.LocalDateTime;

public sealed abstract class AbstractBooking permits ParkingBooking, RestaurantBooking, RoomBooking{

    private int roomNumber;
    private Customer customer;
    private LocalDateTime bookingDateTimeStart;
    private LocalDateTime bookingDateTimeEnd;

    public AbstractBooking(Customer customer, int roomNumber, LocalDateTime bookingDateTimeStart,
                           LocalDateTime bookingDateTimeEnd, String bookingID) {
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
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LocalDateTime getBookingDateTimeStart() {
        return bookingDateTimeStart;
    }
    public LocalDateTime getBookingDateTimeEnd() {
        return bookingDateTimeEnd;
    }
    public abstract String generateBookingID();
    public abstract boolean doesBookingClash();
}
