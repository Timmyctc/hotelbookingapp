package org.dip.tus.core;

import org.dip.tus.customer.Customer;
import org.dip.tus.exception.BookingDateArgumentException;
import org.dip.tus.menu.ConsoleColour;

import java.time.LocalDateTime;

/**
 * Abstract base class for bookings across various entities.
 * Defines the common properties and behavior of bookings such as customer details,
 * start and end date/time, and booking validation logic.
 */
public abstract class AbstractBooking {

    private Customer customer;
    private LocalDateTime bookingDateTimeStart;
    private LocalDateTime bookingDateTimeEnd;

    /**
     * Constructs an AbstractBooking instance with the provided details.
     *
     * @param customer The customer associated with the booking.
     * @param bookingDateTimeStart The start date and time of the booking.
     * @param bookingDateTimeEnd The end date and time of the booking.
     * @throws BookingDateArgumentException if the start date is not before the end date.
     */
    public AbstractBooking(Customer customer, LocalDateTime bookingDateTimeStart,
                           LocalDateTime bookingDateTimeEnd) throws BookingDateArgumentException {
        if (!bookingDateTimeStart.isBefore(bookingDateTimeEnd)) {
            throw new BookingDateArgumentException("Booking start date must be before booking end date");
        }
        this.customer = customer;
        this.bookingDateTimeStart = bookingDateTimeStart;
        this.bookingDateTimeEnd = bookingDateTimeEnd;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getBookingDateTimeStart() {
        return bookingDateTimeStart;
    }

    public LocalDateTime getBookingDateTimeEnd() {
        return bookingDateTimeEnd;
    }

    /**
     * Generates a unique booking ID for the specific type of booking.
     * @return A unique string representing the booking ID.
     */
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
