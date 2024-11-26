package org.dip.tus.room;

import org.dip.tus.core.AbstractBookingManager;
import org.dip.tus.customer.Customer;
import org.dip.tus.menu.ConsoleColour;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RoomManager extends AbstractBookingManager<Room, RoomBooking> {

    private static final RoomManager instance = new RoomManager();

    private RoomManager() {
        initialiseRooms();
    }

    public static RoomManager getInstance() {
        return instance;
    }

    private void initialiseRooms() {
        IntStream.rangeClosed(1, 5).forEach(i -> addEntity(new Room(i, RoomType.SINGLE)));
        IntStream.rangeClosed(6, 10).forEach(i -> addEntity(new Room(i, RoomType.DOUBLE)));
        IntStream.rangeClosed(11, 15).forEach(i -> addEntity(new Room(i, RoomType.KING)));
        IntStream.rangeClosed(16, 20).forEach(i -> addEntity(new Room(i, RoomType.QUEEN)));
    }

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

    public List<RoomBooking> getAllBookingsForCustomer(Customer customer) {
        return  getAllEntities()
                .stream()
                .flatMap(room -> room.getAllBookings().stream())
                .filter(booking -> booking.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }

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
}
