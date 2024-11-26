package org.dip.tus.core;

import org.dip.tus.customer.Customer;
import org.dip.tus.exception.BookingDateArgumentException;
import org.dip.tus.menu.ConsoleColour;

import java.time.LocalDateTime;

public abstract class AbstractBooking {

    private int roomNumber;
    private Customer customer;
    private LocalDateTime bookingDateTimeStart;
    private LocalDateTime bookingDateTimeEnd;

    public AbstractBooking(Customer customer, LocalDateTime bookingDateTimeStart,
                           LocalDateTime bookingDateTimeEnd) throws BookingDateArgumentException {
        if(!bookingDateTimeStart.isBefore(bookingDateTimeEnd)) {
            throw new BookingDateArgumentException("bookingDateTimeStart must be before bookingDateTimeEnd");
        }
        this.customer = customer;
        this.bookingDateTimeStart = bookingDateTimeStart;
        this.bookingDateTimeEnd = bookingDateTimeEnd;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber=roomNumber;
    }

    public LocalDateTime getBookingDateTimeStart() {
        return bookingDateTimeStart;
    }
    public LocalDateTime getBookingDateTimeEnd() {
        return bookingDateTimeEnd;
    }
    public abstract String generateBookingID();

    @Override
    public String toString() {
        return String.format(
                ConsoleColour.BLUE + "Booking Details:\n" + ConsoleColour.RESET +
                        ConsoleColour.GREEN + "Customer: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.CYAN + "Booking Start: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.CYAN + "Booking End: " + ConsoleColour.RESET + "%s\n",
                customer.getName(),
                bookingDateTimeStart,
                bookingDateTimeEnd
        );
    }


}
