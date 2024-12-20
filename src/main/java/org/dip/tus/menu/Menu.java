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
import org.dip.tus.util.InputHelper;

import java.util.Scanner;

/**
 * Class handling the display and navigation of the application's main menu.
 * It provides submenus for managing room, restaurant, and parking reservations,
 * as well as generating reports and viewing customer information.
 */
public class Menu {

    // Managers and Services
    private static final RoomManager roomManager = RoomManager.getInstance();
    private static final RestaurantManager restaurantManager = RestaurantManager.getInstance();
    private static final ParkingLotManager parkingLotManager = ParkingLotManager.getInstance();
    private static final CustomerManager customerManager = CustomerManager.getInstance();
    private static final RoomService roomService = RoomService.getInstance();
    private static final RestaurantService restaurantService = RestaurantService.getInstance();
    private static final ParkingService parkingService = ParkingService.getInstance();
    private static final ReportService reportService = ReportService.getInstance();

    /**
     * Displays the main menu and handles user input for navigating the application.
     */
    public static void displayMenu() {
        boolean menuLoop = true;

        while (menuLoop) {
            displayHeader();
            displayOptions();

            int choice = getInput();

            switch (choice) {
                case 1 -> displaySubMenu("Room", roomSubMenuHandler());
                case 2 -> displaySubMenu("Table", restaurantSubMenuHandler());
                case 3 -> displaySubMenu("Parking Spot", parkingSubMenuHandler());
                case 4 -> viewAllCustomers();
                case 5 -> displayReportMenu();
                case 6 -> {
                    System.out.println("Exiting system...");
                    menuLoop = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Displays the main menu header.
     */
    private static void displayHeader() {
        System.out.println(ConsoleColour.RED);
        System.out.println("***************************************************");
        System.out.println("*           Welcome to Hotel Management           *");
        System.out.println("***************************************************");
        System.out.println(ConsoleColour.WHITE_BOLD_BRIGHT);
    }

    /**
     * Displays the main menu options.
     */
    private static void displayOptions() {
        System.out.println("1) Room Reservations");
        System.out.println("2) Restaurant Reservations");
        System.out.println("3) Parking Reservation");
        System.out.println("4) View All Customer Records");
        System.out.println("5) Generate Reports");
        System.out.println("6) Quit");
        System.out.print("Select an option [1-6]: ");
    }

    /**
     * Displays the report submenu and handles user input for generating various reports.
     */
    private static void displayReportMenu() {
        boolean reportMenuLoop = true;
        while (reportMenuLoop) {
            System.out.println("----------------------------------------------------");
            System.out.println("Report Options:");
            System.out.println("1) All Customers Booking Report");
            System.out.println("2) Customer Booking Report per Customer");
            System.out.println("3) Booking Summary Report");
            System.out.println("4) Financial Report");
            System.out.println("5) Back to Main Menu");
            System.out.println("----------------------------------------------------");
            System.out.print("Select an option [1-5]: ");

            int reportChoice = getInput();

            switch (reportChoice) {
                case 1 -> reportService.generateCustomerBookingReport(customerManager.getCustomerList().toArray(new Customer[0]));
                case 2 -> reportService.generateCustomerBookingReport(
                        customerManager.getCustomer(
                                InputHelper.parseString("Enter customer name: "),
                                InputHelper.parseDateOfBirth("Enter customer date of birth: ")
                        )
                );
                case 3 -> reportService.generateBookingSummaryReport();
                case 4 -> reportService.generateFinancialReport();
                case 5 -> reportMenuLoop = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Displays a submenu for the given reservation type (e.g., Room, Table, Parking).
     *
     * @param keyWord A string representing the reservation type.
     * @param handler A functional interface to handle specific submenu operations.
     */
    private static void displaySubMenu(String keyWord, SubMenuHandler handler) {
        boolean menuLoop = true;
        while (menuLoop) {
            System.out.println("----------------------------------------------------");
            System.out.println(keyWord + " Reservation Options:");
            System.out.println("1) Reserve a " + keyWord);
            System.out.println("2) Remove a " + keyWord + " Reservation");
            System.out.println("3) View All " + keyWord + "s");
            System.out.println("4) Back to Main Menu");
            System.out.println("----------------------------------------------------");
            System.out.print("Select an option [1-4]: ");

            int choice = getInput();
            if (choice == 4) {
                menuLoop = false;
            } else {
                handler.handle(choice);
            }
        }
    }

    /**
     * Prompts the user for input and parses it as an integer.
     *
     * @return The integer entered by the user, or -1 if the input is invalid.
     */
    private static int getInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }

    /**
     * Displays all rooms.
     */
    private static void viewAllRooms() {
        roomManager.displayAvailableRooms(roomManager.getAllEntities());
    }

    /**
     * Displays all restaurant tables.
     */
    private static void viewAllRestaurantTables() {
        restaurantManager.displayAvailableTables(restaurantManager.getAllEntities());
    }

    /**
     * Displays all parking spots.
     */
    private static void viewAllParkingSpots() {
        parkingLotManager.displayAvailableParkingSpots(parkingLotManager.getAllEntities());
    }

    /**
     * Displays all registered customers.
     */
    private static void viewAllCustomers() {
        customerManager.displayAllCustomers();
    }

    // Functional Interfaces

    /**
     * Handles the Room submenu operations.
     *
     * @return Realisation of a SubMenuHandler functional interface to process Room submenu choices.
     */
    private static SubMenuHandler roomSubMenuHandler() {
        return choice -> {
            switch (choice) {
                case 1 -> roomService.handleRoomBooking();
                case 2 -> roomService.removeRoomBooking();
                case 3 -> viewAllRooms();
                default -> System.out.println("Invalid option. Please try again.");
            }
        };
    }

    /**
     * Handles the Restaurant submenu operations.
     *
     * @return Realisation of a SubMenuHandler functional interface to process table submenu choices.
     */
    private static SubMenuHandler restaurantSubMenuHandler() {
        return choice -> {
            switch (choice) {
                case 1 -> restaurantService.handleRestaurantReservation();
                case 2 -> restaurantService.removeRestaurantReservation();
                case 3 -> viewAllRestaurantTables();
                default -> System.out.println("Invalid option. Please try again.");
            }
        };
    }

    /**
     * Handles the Parking submenu operations.
     *
     * @return Realisation of a SubMenuHandler functional interface to process parking submenu choices.
     */
    private static SubMenuHandler parkingSubMenuHandler() {
        return choice -> {
            switch (choice) {
                case 1 -> parkingService.handleParkingReservation();
                case 2 -> parkingService.removeParkingReservation();
                case 3 -> viewAllParkingSpots();
                default -> System.out.println("Invalid option. Please try again.");
            }
        };
    }
}
