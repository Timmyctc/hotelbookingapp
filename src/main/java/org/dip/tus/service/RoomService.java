package org.dip.tus.service;

import org.dip.tus.customer.Customer;
import org.dip.tus.customer.CustomerManager;
import org.dip.tus.exception.BookingDateArgumentException;
import org.dip.tus.menu.ConsoleColour;
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

    private final RoomManager roomManager = RoomManager.getInstance();
    private final CustomerManager customerManager = CustomerManager.getInstance();
    private static final RoomService instance = new RoomService();

    private RoomService(){}

    public static RoomService getInstance() {
        return instance;
    }

    public List<RoomBooking> getAllBookingsForCustomer(Customer customer) {
        return roomManager.getAllEntities()
                .stream()
                .flatMap(room -> room.getAllBookings().stream())
                .filter(booking -> booking.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }

    public List<RoomBooking> getAllBookings() {
        return roomManager.getAllEntities()
                .stream()
                .flatMap(room -> room.getAllBookings().stream())
                .collect(Collectors.toList());
    }

    public void handleRoomBooking() {
        String customerName = "";
        while (customerName.isBlank()) {
            customerName = InputHelper.parseString("Enter customer name: ");
            if(customerName.isBlank()) System.out.println("Name cannot be blank");
        }
        LocalDate dob = InputHelper.parseDate("Enter customer date of birth (YYYY-MM-DD): ");

        RoomType roomType = InputHelper.parseRoomEnum("Enter room type (SINGLE, DOUBLE, KING, QUEEN): ");

        LocalDate startDate = null;
        while (startDate == null || !startDate.isAfter(LocalDate.now())) {
            startDate = InputHelper.parseDate("Enter booking start date (YYYY-MM-DD): ");
            if (!startDate.isAfter(LocalDate.now())) {
                System.out.println("Start time must be later than today. Please try again.");
            }
        }
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

        Room selectedRoom = null;
        while (selectedRoom == null) {
            roomManager.displayAvailableRooms(availableRooms);
            int selectedRoomNumber = InputHelper.parseInt("Enter the room number to reserve: ");
            selectedRoom = roomManager.findEntityById(String.valueOf(selectedRoomNumber));
            if (selectedRoom == null) {
                System.out.println("Invalid room number selected.");
                continue;
            } else if (!availableRooms.contains(selectedRoom)) {
                System.out.println("Not a valid Entry");
                selectedRoom = null;
                continue;
            }
            try {
                RoomBooking booking = new RoomBooking(customer,
                        roomManager.findEntityById(String.valueOf(selectedRoomNumber)),
                        bookingStart, bookingEnd, roomManager.calculateCostForDates(selectedRoom,bookingStart,bookingEnd) );
                if (roomManager.addBookingToEntity(String.valueOf(selectedRoomNumber), booking)) {
                    System.out.println("Room booking successful: \n" + booking);
                } else {
                    System.out.println("Room booking failed due to a conflict.");
                }
            } catch (BookingDateArgumentException e) {
                System.out.println("Booking error: " + e.getMessage());
            }
        }
    }

    private void displayBookingsPerCustomer(List<RoomBooking> customerBookings) {
         System.out.println(ConsoleColour.BLUE + "+-----+----------------------------------------+");
        System.out.println("| No. | Booking Details                        |");
        System.out.println("+-----+----------------------------------------+" + ConsoleColour.RESET);

        int index = 1;
        for (RoomBooking roomBooking : customerBookings) {
            System.out.printf(ConsoleColour.GREEN + "| %-3d | %-38s |\n" + ConsoleColour.RESET,
                    index++,
                    roomBooking.toString());
        }
        System.out.println(ConsoleColour.BLUE + "+-----+----------------------------------------+" + ConsoleColour.RESET);
    }

    public void removeRoomBooking() {
        String customerName = "";
        while (customerName.isBlank()) {
            customerName = InputHelper.parseString("Enter name of customer who made reservation: ");
            if (customerName.isBlank()) System.out.println("Name cannot be blank");
        }
        LocalDate dob = InputHelper.parseDate("Enter customer date of birth (YYYY-MM-DD): ");

        Customer customer = customerManager.getCustomer(customerName, dob);
        List<RoomBooking> customerBookings = roomManager.getAllBookingsForCustomer(customer);
        if(customerBookings.isEmpty()) {
            System.out.println("No Bookings for this customer.");
            return;
        }
        displayBookingsPerCustomer(customerBookings);

        int bookingIndexToRemove = 0;
        while(bookingIndexToRemove <= 0 || bookingIndexToRemove > customerBookings.size()) {
            bookingIndexToRemove = InputHelper.parseInt("Enter number of booking to remove: ");
            if(bookingIndexToRemove < 0 || bookingIndexToRemove > customerBookings.size()) System.out.println("Invalid Index");
        }
        roomManager.removeBookingFromEntity(customerBookings.get(bookingIndexToRemove-1).getRoom().getId(),customerBookings.get(bookingIndexToRemove-1));
        System.out.println("Removed the Booking at " + bookingIndexToRemove);
    }

}
