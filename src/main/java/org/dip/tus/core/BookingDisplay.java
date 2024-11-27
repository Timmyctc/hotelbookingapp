package org.dip.tus.core;

import org.dip.tus.menu.ConsoleColour;

import java.util.List;

public interface BookingDisplay<B> {

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
