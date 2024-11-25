package org.dip.tus.service;

import org.dip.tus.customer.Customer;
import org.dip.tus.customer.CustomerManager;
import org.dip.tus.exception.BookingDateArgumentException;
import org.dip.tus.room.Room;
import org.dip.tus.room.RoomBooking;
import org.dip.tus.room.RoomManager;
import org.dip.tus.room.RoomType;
import org.dip.tus.util.InputHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public final class RoomService {

//    private final RoomManager roomManager = new RoomManager();
//    private final CustomerManager customerManager;
    private static final RoomService instance = new RoomService();

    private RoomService(){}

    public static RoomService getInstance() {
        return instance;
    }

    public void handleRoomBooking() {
        String customerName = InputHelper.parseString("Enter customer name: ");
        LocalDate dob = InputHelper.parseDate("Enter customer date of birth (YYYY-MM-DD): ");

        RoomType roomType = RoomType.valueOf(
                InputHelper.parseString("Enter room type (SINGLE, DOUBLE, KING, QUEEN): ").toUpperCase()
        );

        LocalDate startDate = InputHelper.parseDate("Enter booking start date (YYYY-MM-DD): ");
        LocalDateTime bookingStart = startDate.atTime(12, 0);

        LocalDate endDate = InputHelper.parseDate("Enter booking end date (YYYY-MM-DD): ");
        LocalDateTime bookingEnd = endDate.atTime(11, 0);

        Customer customer = customerManager.getCustomerOrAdd(customerName, dob);

        List<Room> availableRooms = roomManager.getAllEntities()
                .stream()
                .filter(r -> r.getRoomType().equals(roomType))
                .filter(r -> !r.doesBookingClash(bookingStart, bookingEnd))
                .collect(Collectors.toList());

        if (availableRooms.isEmpty()) {
            System.out.println("No rooms of this type are available for the selected dates.");
            return;
        }

        roomManager.displayAvailableRooms(availableRooms);

        int selectedRoomNumber = InputHelper.parseInt("Enter the room number to reserve: ");
        Room selectedRoom = roomManager.findEntityById(String.valueOf(selectedRoomNumber));

        if (selectedRoom == null) {
            System.out.println("Invalid room number selected.");
            return;
        }

        try {
            RoomBooking booking = new RoomBooking(customer, selectedRoomNumber, bookingStart, bookingEnd);
            if (roomManager.addBookingToEntity(String.valueOf(selectedRoomNumber), booking)) {
                System.out.println("Room booking successful: " + booking);
            } else {
                System.out.println("Room booking failed due to a conflict.");
            }
        } catch (BookingDateArgumentException e) {
            System.out.println("Booking error: " + e.getMessage());
        }
    }

    public void viewAllRooms() {
        roomManager.getAllEntities().forEach(System.out::println);
    }
}