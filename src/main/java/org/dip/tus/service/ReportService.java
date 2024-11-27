package org.dip.tus.service;

import org.dip.tus.customer.Customer;
import org.dip.tus.customer.CustomerManager;
import org.dip.tus.menu.ConsoleColour;
import org.dip.tus.parking.ParkingBooking;
import org.dip.tus.parking.ParkingLotManager;
import org.dip.tus.restaurant.RestaurantBooking;
import org.dip.tus.restaurant.RestaurantManager;
import org.dip.tus.room.RoomBooking;
import org.dip.tus.room.RoomManager;
import org.dip.tus.util.InputHelper;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ReportService {

    private static final ReportService instance = new ReportService();
    private static final ParkingService parkingService = ParkingService.getInstance();
    private static final RestaurantService restaurantService = RestaurantService.getInstance();
    private static final RestaurantManager restaurantManager = RestaurantManager.getInstance();
    private static final RoomService roomService = RoomService.getInstance();
    private static final RoomManager roomManager = RoomManager.getInstance();
    private static final ParkingLotManager parkingLotManager = ParkingLotManager.getInstance();
    private static final CustomerManager customerManager = CustomerManager.getInstance();

    private ReportService() {
    }

    public static ReportService getInstance() {
        return instance;
    }

    /**
     * Outputs a report of all customers and their respective bookings (room, parking, restaurant)
     */
    public void generateCustomerBookingReport(Customer... customers) {
         System.out.println("Customer Booking Report:");
        System.out.println("----------------------------------------------------");
        double totalExpenditure = 0;

        for (Customer customer : customers) {
            if(Objects.isNull(customer)) {
                System.out.println("No Customer by that name/dob");
                break;
            }
            System.out.println(ConsoleColour.BLUE + "Customer: " + customer.getName() +
                    " (DOB: " + customer.getDateOfBirth() + ")" + ConsoleColour.RESET);

            // Room Bookings
            List<RoomBooking> roomBookings = roomService.getAllBookingsForCustomer(customer);
            if (!roomBookings.isEmpty()) {
                System.out.println(ConsoleColour.GREEN + "  Room Bookings:" + ConsoleColour.RESET);
                totalExpenditure += roomBookings.stream()
                        .peek(booking -> {
                            double cost = roomManager.calculateCostForBooking(booking);
                            System.out.println("    - " + booking + " | Cost: €" + cost);
                        })
                        .mapToDouble(roomManager::calculateCostForBooking)
                        .sum();
            }

            // Parking Bookings
            List<ParkingBooking> parkingBookings = parkingLotManager.getAllBookingsForCustomer(customer);
            if (!parkingBookings.isEmpty()) {
                System.out.println(ConsoleColour.YELLOW + "  Parking Bookings:" + ConsoleColour.RESET);
                totalExpenditure += parkingBookings.stream()
                        .peek(booking -> {
                            double cost = booking.getCost();
                            System.out.println("    - " + booking + " | Cost: €" + cost);
                        })
                        .mapToDouble(ParkingBooking::getCost)
                        .sum();
            }

            // Restaurant Bookings
            List<RestaurantBooking> restaurantBookings = restaurantManager.getAllBookingsForCustomer(customer);
            if (!restaurantBookings.isEmpty()) {
                System.out.println(ConsoleColour.PURPLE + "  Restaurant Bookings:" + ConsoleColour.RESET);
                totalExpenditure += restaurantBookings.stream()
                        .peek(booking -> {
                            double cost = booking.getCost();
                            System.out.println("    - " + booking + " | Cost: €" + cost);
                        })
                        .mapToDouble(RestaurantBooking::getCost)
                        .sum();
            }

            System.out.println("----------------------------------------------------");
        }

        System.out.println(ConsoleColour.RED + "Total Expenditure: €" + totalExpenditure + ConsoleColour.RESET);
    }


    /**
     * Outputs a summary of all bookings by type
     */
    public void generateBookingSummaryReport() {
        System.out.println("Booking Summary Report:");
        System.out.println("----------------------------------------------------");

        System.out.println(ConsoleColour.GREEN + "Room Bookings:" + ConsoleColour.RESET);
        if (roomService.getAllBookings().isEmpty()) System.out.println("No Bookings to Display\n");
        else roomService.getAllBookings().forEach(System.out::println);

        System.out.println(ConsoleColour.YELLOW + "Parking Bookings:" + ConsoleColour.RESET);
        if (parkingService.getAllBookings().isEmpty()) System.out.println("No Bookings to Display\n");
        else parkingService.getAllBookings().forEach(System.out::println);

        System.out.println(ConsoleColour.PURPLE + "Restaurant Bookings:" + ConsoleColour.RESET);
        if (restaurantService.getAllBookings().isEmpty()) System.out.println("No Bookings to Display\n");
        else restaurantService.getAllBookings().forEach(System.out::println);

        System.out.println("----------------------------------------------------");
    }

    /**
     * Outputs a financial report for all bookings and total revenue
     */
    public void generateFinancialReport() {
        System.out.println("Financial Report:");
        System.out.println("----------------------------------------------------");

        double roomRevenue = roomService.getAllBookings().stream()
                .mapToDouble(roomManager::calculateCostForBooking)
                .sum();
        System.out.println(ConsoleColour.GREEN + "Total Room Revenue: €" + roomRevenue + ConsoleColour.RESET);

        double parkingRevenue = parkingService.getAllBookings().stream()
                .mapToDouble(parkingLotManager::calculateCostForBooking)
                .sum();
        System.out.println(ConsoleColour.YELLOW + "Total Parking Revenue: €" + parkingRevenue + ConsoleColour.RESET);

        double restaurantRevenue = restaurantService.getAllBookings().stream()
                .mapToDouble(RestaurantBooking::getCost)
                .sum();
        System.out.println(ConsoleColour.PURPLE + "Total Restaurant Revenue: €" + restaurantRevenue + ConsoleColour.RESET);

        double totalRevenue = roomRevenue + parkingRevenue + restaurantRevenue;
        System.out.println(ConsoleColour.BLUE + "Total Revenue: €" + totalRevenue + ConsoleColour.RESET);
        System.out.println("----------------------------------------------------");
    }

    public Optional<Customer> getCustomerNameOrNull() {
        String name = InputHelper.parseString("Enter customer name or leave empty to return All customer record: ");
        if (name.isEmpty()) return Optional.empty();
        else {
            LocalDate dob = InputHelper.parseDate("Enter customer date of birth: ");
            return Optional.ofNullable(customerManager.getCustomer(name, dob));
        }
    }
}
