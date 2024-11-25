package org.dip.tus.room;

import org.dip.tus.core.AbstractBookingManager;
import org.dip.tus.menu.ConsoleColour;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class RoomManager extends AbstractBookingManager<Room, RoomBooking> {

    private static final RoomManager instance = new RoomManager();
//    private final Map<Integer, Room> roomRegistry = new HashMap<>();

    private RoomManager() {
        initialiseRooms();
    }

    public static RoomManager getInstance() {
        return instance;
    }

    private void initialiseRooms() {
        IntStream.rangeClosed(1, 10).forEach(i -> addEntity(new Room(i, RoomType.SINGLE)));
        IntStream.rangeClosed(11, 20).forEach(i -> addEntity(new Room(i, RoomType.DOUBLE)));
        IntStream.rangeClosed(21, 30).forEach(i -> addEntity(new Room(i, RoomType.KING)));
        IntStream.rangeClosed(31, 40).forEach(i -> addEntity(new Room(i, RoomType.QUEEN)));
    }

        public static void displayAvailableRooms(List<Room> availableRooms) {
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


    }