package org.dip.tus.core;

import org.dip.tus.menu.ConsoleColour;

import java.util.List;

/**
 * An interface providing a default method to display a formatted list of bookings.
 * Implemented by the Service classes for display purposes.
 *
 * @param <B> the type of booking to be displayed. This should extend {@link AbstractBooking}.
 */
public interface BookingDisplay<B> {

    /**
     * Displays formatted list of bookings
     * Each booking is numbered sequentially (1 based index) for easy reference.
     *
     * @param bookings the list of bookings to be displayed.
     *                 Each booking's {@code toString()} method is used to generate its details.
     */
    default void displayBookings(List<B> bookings) {
        System.out.println(ConsoleColour.BLUE + "+-----+----------------------------------------+");
        System.out.println("| No. | Booking Details                        |");
        System.out.println("+-----+----------------------------------------+" + ConsoleColour.RESET);

        int index = 1;
        for (B booking : bookings) {
            System.out.printf(ConsoleColour.GREEN + "| %-3d | %-38s |\n" + ConsoleColour.RESET,
                    index++, booking.toString());
        }
        System.out.println(ConsoleColour.BLUE + "+-----+----------------------------------------+" + ConsoleColour.RESET);
    }
}
