package org.dip.tus.service;

import org.dip.tus.core.BookingDisplay;
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

/**
 * singleton Service class for handling restaurant-related operations, including reservations and cancellations.
 */
public final class RestaurantService implements BookingDisplay<RestaurantBooking> {

    private final RestaurantManager restaurantManager = RestaurantManager.getInstance();
    private final CustomerManager customerManager = CustomerManager.getInstance();
    private static final RestaurantService instance = new RestaurantService();

    private RestaurantService() {
    }

    public static RestaurantService getInstance() {
        return instance;
    }

    /**
     * Handles the process of reserving a table for a customer in the restaurant.
     * Includes input validation for customer details, reservation timing, and table selection.
     */
    public void handleRestaurantReservation() {
        String customerName = "";
        while (customerName.isBlank()) {
            customerName = InputHelper.parseString("Enter customer name: ");
            if (customerName.isBlank()) System.out.println("Name cannot be blank!");
        }
        LocalDate dob = InputHelper.parseDateOfBirth("Enter customer date of birth (YYYY-MM-DD): ");

        int numberOfPeople = InputHelper.parseInt("Enter the number of people for the reservation: ");

        LocalDateTime startTime = null;
        while (startTime == null || !startTime.isAfter(LocalDateTime.now())) {
            startTime = InputHelper.parseDateTime("Enter booking start time (YYYY-MM-DDTHH:MM): ");
            if (!startTime.isAfter(LocalDateTime.now())) {
                System.out.println("Start time must be today or later. Please try again.");
            }
        }
        int duration;
        do {
            duration = InputHelper.parseInt("Enter the reservation duration in hours: ");
            if (duration < 1 || duration > 3)
                System.out.println("Restaurant Reservation must be between 1 to 3 hours long.");
        } while (duration < 1 || duration > 3);
        LocalDateTime endTime = startTime.plusHours(duration);

        Customer customer = customerManager.getCustomerOrAdd(customerName, dob);

        LocalDateTime finalStartTime = startTime;
        Table suitableTable = restaurantManager.getAllEntities()
                .stream()
                .filter(t -> t.getNumberofSeats() == numberOfPeople)
                .filter(t -> !t.doesBookingClash(finalStartTime, endTime))
                .findAny()
                .orElseGet(() ->
                        restaurantManager.getAllEntities()
                                .stream()
                                .filter(t -> t.getNumberofSeats() > numberOfPeople)
                                .filter(t -> !t.doesBookingClash(finalStartTime, endTime))
                                .findAny()
                                .orElse(null));

        if (!Objects.nonNull(suitableTable)) {
            System.out.println("No tables available for the selected time and table size.");
            return;
        }
        try {
            RestaurantBooking booking = new RestaurantBooking(customer, startTime, endTime, suitableTable, numberOfPeople);
            if (restaurantManager.addBookingToEntity(String.valueOf(suitableTable.getTableNumber()), booking)) {
                System.out.println("Restaurant reservation successful: " + booking);
            } else {
                System.out.println("Reservation failed due to a conflict.");
            }
        } catch (BookingDateArgumentException e) {
            System.out.println("Reservation error: " + e.getMessage());
        }
    }

    /**
     * Retrieves all restaurant bookings.
     *
     * @return A list of all restaurant bookings.
     */
    public List<RestaurantBooking> getAllBookings() {
        return restaurantManager.getAllEntities()
                .stream()
                .flatMap(room -> room.getAllBookings().stream())
                .collect(Collectors.toList());
    }

    /**
     * Handles the process of removing a restaurant reservation.
     * Prompts the user for customer details and allows selection of the reservation to be removed.
     */
    public void removeRestaurantReservation() {
        String customerName;
        do {
            customerName = InputHelper.parseString("Enter name of customer who made reservation: ").trim();
            if (customerName.isBlank()) {
                System.out.println("Name cannot be blank");
            } else {
                try {
                    if (Integer.parseInt(customerName) == -1) return;
                } catch (NumberFormatException ignored) {
                }
            }
        } while (customerName.isBlank());

        LocalDate dob = InputHelper.parseDateOfBirth("Enter customer date of birth (YYYY-MM-DD): ");

        Customer customer = customerManager.getCustomer(customerName, dob);
        List<RestaurantBooking> customerBookings = restaurantManager.getAllBookingsForCustomer(customer);

        if (customerBookings.isEmpty()) {
            System.out.println("No Bookings for this customer.");
            return;
        }

        displayBookings(customerBookings);

        int bookingIndexToRemove;
        do {
            bookingIndexToRemove = InputHelper.parseInt("Enter number of booking to remove: ");
            if (bookingIndexToRemove <= 0 || bookingIndexToRemove > customerBookings.size()) {
                System.out.println("Invalid Index");
            }
        } while (bookingIndexToRemove <= 0 || bookingIndexToRemove > customerBookings.size());

        RestaurantBooking bookingToRemove = customerBookings.get(bookingIndexToRemove - 1);
        restaurantManager.removeBookingFromEntity(
                String.valueOf(bookingToRemove.getTable().getTableNumber()),
                bookingToRemove
        );
        System.out.println("Removed the Booking at " + bookingIndexToRemove);
    }
}
