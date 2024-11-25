package org.dip.tus.menu;

import org.dip.tus.customer.Customer;
import org.dip.tus.customer.CustomerManager;
import org.dip.tus.exception.BookingDateArgumentException;
import org.dip.tus.parking.ParkingBooking;
import org.dip.tus.parking.ParkingLotManager;
import org.dip.tus.room.Room;
import org.dip.tus.room.RoomBooking;
import org.dip.tus.room.RoomManager;
import org.dip.tus.restaurant.RestaurantBooking;
import org.dip.tus.restaurant.RestaurantManager;
import org.dip.tus.room.RoomType;
import org.dip.tus.service.RoomService;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;

public class Menu {

    private static final RoomManager roomManager = RoomManager.getInstance();
    private static final RestaurantManager restaurantManager = RestaurantManager.getInstance() ;
    private static final ParkingLotManager parkingLotManager = ParkingLotManager.getInstance();
    private static final CustomerManager customerManager = CustomerManager.getInstance();
    private static final RoomService roomService = roomService;


    public static void displayMenu() {
        boolean menuLoop = true;

        while (menuLoop) {
            displayHeader();
            displayOptions();

            int choice = getInput();

            switch (choice) {
                case 1 -> ();
                case 2 -> handleRestaurantReservation();
                case 3 -> handleParkingReservation();
                case 4 -> viewAllRooms();
                case 5 -> viewAllRestaurantTables();
                case 6 -> viewAllCustomers();
                case 7 -> {
                    System.out.println("Exiting system...");
                    menuLoop = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void displayHeader() {
        System.out.println(ConsoleColour.RED);
        System.out.println("***************************************************");
        System.out.println("*           Welcome to Hotel Management           *");
        System.out.println("***************************************************");
//        System.out.println(ConsoleColour.BLACK_BACKGROUND);
        System.out.println(ConsoleColour.WHITE_BOLD_BRIGHT);
    }

    private static void displayOptions() {
        System.out.println("1) Make a Room Reservation");
        System.out.println("2) Make a Restaurant Reservation");
        System.out.println("3) Make a Parking Reservation");
        System.out.println("4) View All Rooms");
        System.out.println("5) View All Restaurant Tables");
        System.out.println("6) View All Customer Records");
        System.out.println("7) Quit");
        System.out.print("Select an option [1-5]: ");
    }

    private static int getInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }

//    private static void handleRoomBooking() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.print("Enter customer name: ");
//        String customerName = scanner.nextLine().trim();
//
//        LocalDate dob = null;
//        while (dob == null) {
//            try {
//                System.out.print("Enter customer date of birth (YYYY-MM-DD): ");
//                dob = LocalDate.parse(scanner.nextLine());
//            } catch (Exception e) {
//                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
//            }
//        }
//
//        System.out.print("Enter room type (SINGLE, DOUBLE, KING, QUEEN): ");
//        RoomType roomType = RoomType.valueOf(scanner.nextLine().toUpperCase());
//
//        LocalDate startDate = null;
//        while (startDate == null) {
//            try {
//                System.out.print("Enter booking start date in YYYY-MM-DD (Note all room bookings start at 12 midday by default): ");
//                startDate = LocalDate.parse(scanner.nextLine());
//            } catch (Exception e) {
//                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
//            }
//        }
//        LocalDateTime bookingStart = startDate.atTime(12,00,00);
//
//        LocalDate endDate = null;
//        while (endDate == null) {
//            try {
//                System.out.print("Enter booking end date in YYYY-MM-DD (Note all room bookings end at 11am by default): ");
//                endDate = LocalDate.parse(scanner.nextLine());
//                if(endDate.isBefore(startDate)) {
//                    System.out.println("End Date Cant be before Start Date");
//                    endDate = null;
//                }
//            } catch (DateTimeException dte) {
//                System.out.println(dte);
//            } catch (Exception e) {
//                System.out.println("Wrong Format");
//            }
//        }
//        LocalDateTime bookingEnd = endDate.atTime(11,00,00);
//
//        Customer customer = customerManager.getCustomerOrAdd(customerName, dob);
//
//        List<Room> availableRooms = roomManager.getAllEntities()
//                .stream()
//                .filter(r -> r.getRoomType().equals(roomType))
//                .filter(r -> !r.doesBookingClash(bookingStart, bookingEnd))
//                .collect(Collectors.toList());
//        roomManager.displayAvailableRooms(availableRooms);
//
//        if(availableRooms.isEmpty()) {
//            System.out.println("No Rooms for that type on that date available, try again");
//            return;
//        }
//        else {
//            int selectedRoom = parseInt(scanner, "Which Room to Reserve");
//                if(availableRooms.contains(roomManager.findEntityById(Integer.toString(selectedRoom)))) {
//
//                }
//            RoomBooking booking = null;
//            try {
//                booking = new RoomBooking(customer,selectedRoom,bookingStart,bookingEnd);
//            } catch (BookingDateArgumentException e) {
//                System.out.println("AHHHHHH");
//                return;
//            }
//            if (roomManager.addBookingToEntity(String.valueOf(selectedRoom), booking)) {
//            System.out.println("Room booking successful.");
//                System.out.println(booking);
//        } else {
//            System.out.println("Room booking failed. It may conflict with another booking.");
//            }
//        }
//    }

    private static void handleRestaurantReservation() {
        Scanner scanner = new Scanner(System.in);
        int people = parseInt(scanner,"How many people in the reservation?");

        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine().trim();

        LocalDate dob = null;
        while (dob == null) {
            try {
                System.out.print("Enter customer date of birth (YYYY-MM-DD): ");
                dob = LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        int tableNumber = parseInt(scanner, "Enter table number  :");

        LocalDateTime startTime = null;
        while (startTime == null) {
            try {
                System.out.print("Enter reservation start time (YYYY-MM-DDTHH:MM): ");
                startTime = LocalDateTime.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DDTHH:MM.");
            }
        }
        LocalDateTime endTime = null;
                int length = parseInt(scanner, "How long is the reservation in hours? ");
                endTime = startTime.plusHours(Long.valueOf(length));


        Customer customer = customerManager.getCustomerOrAdd(customerName, dob);
        RestaurantBooking booking;
        try {
            booking = new RestaurantBooking(customer, startTime, endTime, tableNumber);
        } catch (BookingDateArgumentException e) {
            System.out.println("Reservation could not be processed: " + e.getMessage());
            return;
        }

        restaurantManager.getOrCreateTable(tableNumber, people);
        if (restaurantManager.addBookingToEntity(String.valueOf(tableNumber), booking)) {
            System.out.println("Restaurant reservation successful.");
        } else {
            System.out.println("Reservation failed. It may conflict with another reservation.");
        }
    }

    private static void handleParkingReservation() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine().trim();

        LocalDate dob = null;
        while (dob == null) {
            try {
                System.out.print("Enter customer date of birth (YYYY-MM-DD): ");
                dob = LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        System.out.print("Enter vehicle registration number: ");
        String vehicleRegistration = scanner.nextLine().trim();

        System.out.print("Enter parking spot section (A-F): ");
        char section = scanner.nextLine().trim().toUpperCase().charAt(0);

        int spotNumber = parseInt(scanner, "Enter parking spot number (1-20):");

        LocalDateTime startTime = null;
        while (startTime == null) {
            try {
                System.out.print("Enter parking start time (YYYY-MM-DDTHH:MM): ");
                startTime = LocalDateTime.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DDTHH:MM.");
            }
        }
        int length = parseInt(scanner, "How long is the reservation in hours? ");
        LocalDateTime endTime = startTime.plusHours(length);

        Customer customer = customerManager.getCustomerOrAdd(customerName, dob);

        ParkingBooking booking;
        try {
            booking = new ParkingBooking(customer, spotNumber, startTime, endTime, vehicleRegistration);
        } catch (BookingDateArgumentException e) {
            System.out.println("Reservation could not be processed: " + e.getMessage());
            return;
        }

        // Step 9: Manage parking spot and add booking
        parkingLotManager.getOrCreateParkingSpot(section, spotNumber);
        if (parkingLotManager.addBookingToEntity(section + String.valueOf(spotNumber), booking)) {
            System.out.println("Parking reservation successful.");
        } else {
            System.out.println("Reservation failed. It may conflict with another reservation.");
        }
    }



    private static void viewAllRooms() {
        System.out.println("Rooms:");
        roomManager.getAllEntities().forEach(System.out::println);
    }

    private static void viewAllRestaurantTables() {
        System.out.println("Restaurant Tables:");
        restaurantManager.getAllEntities().forEach(System.out::println);
    }
    private static void viewAllCustomers() {
        System.out.println("Customers:");
        customerManager.getCustomerList().forEach(System.out::println);
    }

    private static int parseInt(Scanner scanner, String prompt) {
        Integer value = null;
        while (value == null || value < 0) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid integer.");
            }
        }
        scanner.reset();
        return value;
    }
}
