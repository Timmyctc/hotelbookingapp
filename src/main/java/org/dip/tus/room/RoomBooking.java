package org.dip.tus.room;

import org.dip.tus.customer.Customer;
import org.dip.tus.core.AbstractBooking;
import org.dip.tus.exception.BookingDateArgumentException;
import org.dip.tus.menu.ConsoleColour;

import java.time.LocalDateTime;

public final class RoomBooking extends AbstractBooking {

    private Room room;

    public RoomBooking(Customer customer, Room room, LocalDateTime startDateTime, LocalDateTime endDateTime) throws BookingDateArgumentException {
        super(customer, startDateTime, endDateTime);
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public int getRoomNumber() {
        return room.getRoomNumber();
    }

    @Override
    public String generateBookingID() {
        return new StringBuilder()
                .append("R")
                .append(getCustomer().hashCode() + getRoomNumber() + getBookingDateTimeStart().hashCode())
                .toString();
    }

    @Override
    public String toString() {
        return String.format(
                ConsoleColour.BLUE + "Room Booking Details:\n" + ConsoleColour.RESET +
                        ConsoleColour.GREEN + "Customer: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.YELLOW + "Room Number: " + ConsoleColour.RESET + "%d\n" +
                        ConsoleColour.CYAN + "Start DateTime: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.CYAN + "End DateTime: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.RED + "Booking ID: " + ConsoleColour.RESET + "%s\n",
                getCustomer().getName(),
                getRoomNumber(),
                getBookingDateTimeStart(),
                getBookingDateTimeEnd(),
                generateBookingID()
        );
    }



}
