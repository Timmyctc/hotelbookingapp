package org.dip.tus.service;

import org.dip.tus.customer.Customer;
import org.dip.tus.customer.CustomerManager;
import org.dip.tus.exception.BookingDateArgumentException;
import org.dip.tus.restaurant.RestaurantBooking;
import org.dip.tus.restaurant.RestaurantManager;
import org.dip.tus.restaurant.Table;
import org.dip.tus.util.InputHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class RestaurantService {

    private final RestaurantManager restaurantManager = RestaurantManager.getInstance();
    private final CustomerManager customerManager = CustomerManager.getInstance();
    private static final RestaurantService instance = new RestaurantService();

    private RestaurantService() {}

    public static RestaurantService getInstance() {
        return instance;
    }

    public void handleRestaurantReservation() {
        String customerName = InputHelper.parseString("Enter customer name: ");
        LocalDate dob = InputHelper.parseDate("Enter customer date of birth (YYYY-MM-DD): ");

        int numberOfPeople = InputHelper.parseInt("Enter the number of people for the reservation: ");

        LocalDateTime startTime = InputHelper.parseDateTime("Enter reservation start time (YYYY-MM-DDTHH:MM): ");
        int duration = InputHelper.parseInt("Enter the reservation duration in hours: ");
        LocalDateTime endTime = startTime.plusHours(duration);

        Customer customer = customerManager.getCustomerOrAdd(customerName, dob);

        Table suitableTable = restaurantManager.getAllEntities()
                .stream()
                .filter(t -> t.getNumberofSeats() == numberOfPeople)
                .filter(t -> !t.doesBookingClash(startTime, endTime))
                .findAny()
                .orElseGet(() ->
                        restaurantManager.getAllEntities()
                                .stream()
                                .filter(t -> t.getNumberofSeats() > numberOfPeople)
                                .filter(t -> !t.doesBookingClash(startTime, endTime))
                                .findAny()
                                .orElse(null));

        if (!Objects.nonNull(suitableTable)) {
            System.out.println("No tables available for the selected time and table size.");
            return;
        }
        try {
            RestaurantBooking booking = new RestaurantBooking(customer, startTime, endTime, suitableTable);
            if (restaurantManager.addBookingToEntity(String.valueOf(suitableTable.getTableNumber()), booking)) {
                System.out.println("Restaurant reservation successful: " + booking);
            } else {
                System.out.println("Reservation failed due to a conflict.");
            }
        } catch (BookingDateArgumentException e) {
            System.out.println("Reservation error: " + e.getMessage());
        }
    }

    public void viewAllTables() {
        restaurantManager.getAllEntities().forEach(System.out::println);
    }
}
