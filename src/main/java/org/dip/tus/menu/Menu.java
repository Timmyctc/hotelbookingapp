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
import org.dip.tus.service.ParkingService;
import org.dip.tus.service.RestaurantService;
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
    private static final RoomService roomService = RoomService.getInstance();
    private static final RestaurantService restaurantService = RestaurantService.getInstance();
    private static final ParkingService parkingService = ParkingService.getInstance();


    public static void displayMenu() {
        boolean menuLoop = true;

        while (menuLoop) {
            displayHeader();
            displayOptions();

            int choice = getInput();

            switch (choice) {
                case 1 -> roomService.handleRoomBooking();
                case 2 -> restaurantService.handleRestaurantReservation();
                case 3 -> parkingService.handleParkingReservation();
                case 4 -> viewAllRooms();
                case 5 -> viewAllRestaurantTables();
                case 6 -> viewAllParkingSpots();
                case 7 -> viewAllCustomers();
                case 8 -> {
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
        System.out.println(ConsoleColour.WHITE_BOLD_BRIGHT);
    }

    private static void displayOptions() {
        System.out.println("1) Make a Room Reservation");
        System.out.println("2) Make a Restaurant Reservation");
        System.out.println("3) Make a Parking Reservation");
        System.out.println("4) View All Rooms");
        System.out.println("5) View All Restaurant Tables");
        System.out.println("6) View All Parking Spots");
        System.out.println("7) View All Customer Records");
        System.out.println("8) Quit");
        System.out.print("Select an option [1-8]: ");
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

    private static void viewAllRooms() {
        roomManager.displayAvailableRooms(roomManager.getAllEntities());
    }

    private static void viewAllRestaurantTables() {
        restaurantManager.displayAvailableTables(restaurantManager.getAllEntities());
    }

    private static void viewAllParkingSpots() {
        parkingLotManager.displayAvailableParkingSpots(parkingLotManager.getAllEntities());
    }

    private static void viewAllCustomers() {
      customerManager.displayAllCustomers();
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
