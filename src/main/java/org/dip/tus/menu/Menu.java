package org.dip.tus.menu;

import org.dip.tus.customer.Customer;
import org.dip.tus.customer.CustomerManager;
import org.dip.tus.parking.ParkingLotManager;
import org.dip.tus.room.RoomManager;
import org.dip.tus.restaurant.RestaurantManager;
import org.dip.tus.service.ParkingService;
import org.dip.tus.service.ReportService;
import org.dip.tus.service.RestaurantService;
import org.dip.tus.service.RoomService;

import java.util.Scanner;


public class Menu {

    private static final RoomManager roomManager = RoomManager.getInstance();
    private static final RestaurantManager restaurantManager = RestaurantManager.getInstance() ;
    private static final ParkingLotManager parkingLotManager = ParkingLotManager.getInstance();
    private static final CustomerManager customerManager = CustomerManager.getInstance();
    private static final RoomService roomService = RoomService.getInstance();
    private static final RestaurantService restaurantService = RestaurantService.getInstance();
    private static final ParkingService parkingService = ParkingService.getInstance();
    private static final ReportService reportService = ReportService.getInstance();


    public static void displayMenu() {
        boolean menuLoop = true;

        while (menuLoop) {
            displayHeader();
            displayOptions();

            int choice = getInput();

            switch (choice) {
                case 1 -> displayRoomMenu();
                case 2 -> restaurantService.handleRestaurantReservation();
                case 3 -> parkingService.handleParkingReservation();
                case 4 -> viewAllRooms();
                case 5 -> viewAllRestaurantTables();
                case 6 -> viewAllParkingSpots();
                case 7 -> viewAllCustomers();
                case 8 -> displayReportMenu();
                case 9 -> {
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
        System.out.println("1) Room Reservations");
        System.out.println("2) Make a Restaurant Reservation");
        System.out.println("3) Make a Parking Reservation");
        System.out.println("4) View All Rooms");
        System.out.println("5) View All Restaurant Tables");
        System.out.println("6) View All Parking Spots");
        System.out.println("7) View All Customer Records");
        System.out.println("8) Generate Reports");
        System.out.println("9) Quit");
        System.out.print("Select an option [1-9]: ");
    }
    private static void displayReportMenu() {
        boolean reportMenuLoop = true;
        while (reportMenuLoop) {
            System.out.println("----------------------------------------------------");
            System.out.println("Report Options:");
            System.out.println("1) Customer Booking Report");
            System.out.println("2) Booking Summary Report");
            System.out.println("3) Financial Report");
            System.out.println("4) Back to Main Menu");
            System.out.println("----------------------------------------------------");
            System.out.print("Select an option [1-4]: ");

            int reportChoice = getInput();

            switch (reportChoice) {
                case 1 -> reportService.generateCustomerBookingReport(customerManager.getCustomerList().toArray(new Customer[0]));
                case 2 -> reportService.generateBookingSummaryReport();
                case 3 -> reportService.generateFinancialReport();
                case 4 -> reportMenuLoop = false; // Exit report menu
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void displayRoomMenu() {
        boolean roomMenuLoop = true;
        while (roomMenuLoop) {
            System.out.println("----------------------------------------------------");
            System.out.println("Room Reservation Options:");
            System.out.println("1) Reserve a Room");
            System.out.println("2) Remove a Room Reservation");
            System.out.println("3) Back to Main Menu");
            System.out.println("----------------------------------------------------");
            System.out.print("Select an option [1-3]: ");

            int roomChoice = getInput();

            switch (roomChoice) {
                case 1 -> roomService.handleRoomBooking();
                case 2 -> roomService.removeRoomBooking();
                case 3 -> roomMenuLoop = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
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
}
