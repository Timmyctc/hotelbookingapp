package org.dip.tus.service;

import org.dip.tus.customer.Customer;
import org.dip.tus.customer.CustomerManager;
import org.dip.tus.exception.BookingDateArgumentException;
import org.dip.tus.restaurant.RestaurantBooking;
import org.dip.tus.restaurant.RestaurantManager;
import org.dip.tus.restaurant.Table;
import org.dip.tus.room.RoomBooking;
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
        String customerName = "";
        while(customerName.isBlank()) {
            customerName = InputHelper.parseString("Enter customer name: ");
            if(customerName.isBlank()) System.out.println("Name cannot be blank!");
        }
        LocalDate dob = InputHelper.parseDate("Enter customer date of birth (YYYY-MM-DD): ");

        int numberOfPeople = InputHelper.parseInt("Enter the number of people for the reservation: ");

        LocalDateTime startTime = null;
        while(startTime == null || !startTime.isAfter(LocalDateTime.now())) {
            startTime = InputHelper.parseDateTime("Enter booking start time (YYYY-MM-DDTHH:MM): ");
            if (!startTime.isAfter(LocalDateTime.now())) {
                System.out.println("Start time must be today or later. Please try again.");
            }
        }
        int duration;
        do {
            duration = InputHelper.parseInt("Enter the reservation duration in hours: ");
            if(duration <1 || duration > 3) System.out.println("Restaurant Reservation must be between 1 to 3 hours long.");
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
    public List<RestaurantBooking> getAllBookings() {
        return restaurantManager.getAllEntities()
                .stream()
                .flatMap(room -> room.getAllBookings().stream())
                .collect(Collectors.toList());
    }
}
