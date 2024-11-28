package org.dip.tus.room;

import org.dip.tus.core.AbstractBookingManager;
import org.dip.tus.customer.Customer;
import org.dip.tus.menu.ConsoleColour;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Singleton Manages room entities and room bookings, including their initialization, availability,
 * and cost calculation.
 */
public class RoomManager extends AbstractBookingManager<Room, RoomBooking> {

    private static final RoomManager instance = new RoomManager();

    private RoomManager() {
        initialiseRooms();
    }

    public static RoomManager getInstance() {
        return instance;
    }

    /**
     * Initializes rooms by adding predefined room types and numbers.
     * Creates rooms grouped by their type.
     */
    private void initialiseRooms() {
        IntStream.rangeClosed(1, 5).forEach(i -> addEntity(new Room(i, RoomType.SINGLE)));
        IntStream.rangeClosed(6, 10).forEach(i -> addEntity(new Room(i, RoomType.DOUBLE)));
        IntStream.rangeClosed(11, 15).forEach(i -> addEntity(new Room(i, RoomType.KING)));
        IntStream.rangeClosed(16, 20).forEach(i -> addEntity(new Room(i, RoomType.QUEEN)));
    }

    /**
     * Displays a list of available rooms, grouped by room type, and includes booking counts.
     *
     * @param availableRooms the list of available rooms to display.
     */
    public void displayAvailableRooms(List<Room> availableRooms) {
        if (availableRooms.isEmpty()) {
            System.out.println(ConsoleColour.RED + "No rooms are currently available." + ConsoleColour.RESET);
            return;
        }

        System.out.println(ConsoleColour.BLUE + "+---------------------------------------------+");
        System.out.println("|              Available Rooms                |");
        System.out.println("+---------------------------------------------+");
        System.out.println("| Room Number | Room Type     | Total Bookings |");
        System.out.println("+-------------+---------------+----------------+" + ConsoleColour.RESET);

        for (Room room : availableRooms) {
            ConsoleColour color = switch (room.getRoomType()) {
                case SINGLE -> ConsoleColour.GREEN;
                case DOUBLE -> ConsoleColour.CYAN;
                case KING -> ConsoleColour.YELLOW;
                case QUEEN -> ConsoleColour.PURPLE;
            };
            System.out.printf(color + "| %-11d | %-13s | %-14d |\n" + ConsoleColour.RESET,
                    room.getRoomNumber(),
                    room.getRoomType(),
                    room.getAllBookings().size());
        }
        System.out.println(ConsoleColour.BLUE + "+---------------------------------------------+" + ConsoleColour.RESET);
    }

    /**
     * Retrieves all bookings associated with a given customer.
     *
     * @param customer the customer whose bookings are to be retrieved.
     * @return a list of room bookings for the specified customer.
     */
    public List<RoomBooking> getAllBookingsForCustomer(Customer customer) {
        return getAllEntities()
                .stream()
                .flatMap(room -> room.getAllBookings().stream())
                .filter(booking -> booking.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the cost of booking a specific room over a given time period given a booking and a room object
     * factoring in the higher weekend rate of 1.5* base cost
     *
     * @param roomBooking the room booking whose cost is to be calculated.
     * @return the total cost of the booking.
     */
    public double calculateCostForBooking(RoomBooking roomBooking) {
        long days = java.time.temporal.ChronoUnit.DAYS.between(roomBooking.getBookingDateTimeStart().toLocalDate(), roomBooking.getBookingDateTimeEnd().toLocalDate());
        Room room = roomBooking.getRoom();
        double baseCost = room.getBaseCost();
        double totalCost = 0;

        LocalDateTime currentTime = roomBooking.getBookingDateTimeStart();
        for (int i = 0; i < days; i++) {
            double dailyRate = baseCost;
            if (isWeekend(currentTime)) {
                dailyRate *= 1.5; // Weekend multiplier
            }
            totalCost += dailyRate;
            currentTime = currentTime.plusDays(1);
        }
        return totalCost;
    }

    /**
     * Calculates the cost of booking a specific room over a given time period given dates and a room object
     * factoring in the higher weekend rate of 1.5* base cost
     *
     * @param room  the room being booked.
     * @param start the start time of the booking.
     * @param end   the end time of the booking.
     * @return the total cost for the specified dates.
     */
    public double calculateCostForDates(Room room, LocalDateTime start, LocalDateTime end) {
        long days = java.time.temporal.ChronoUnit.DAYS.between(start.toLocalDate(), end.toLocalDate());
        double baseCost = room.getBaseCost();
        double totalCost = 0;

        LocalDateTime currentTime = start;
        for (int i = 0; i < days; i++) {
            double dailyRate = baseCost;
            if (isWeekend(currentTime)) {
                dailyRate *= 1.5;
            }
            totalCost += dailyRate;
            currentTime = currentTime.plusDays(1);
        }
        return totalCost;
    }
}
