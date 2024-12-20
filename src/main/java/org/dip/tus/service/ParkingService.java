package org.dip.tus.service;

import org.dip.tus.core.BookingDisplay;
import org.dip.tus.customer.Customer;
import org.dip.tus.customer.CustomerManager;
import org.dip.tus.exception.BookingDateArgumentException;
import org.dip.tus.menu.ConsoleColour;
import org.dip.tus.parking.ParkingBooking;
import org.dip.tus.parking.ParkingLotManager;
import org.dip.tus.parking.ParkingSpot;
import org.dip.tus.util.InputHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Singleton Service class for handling parking reservations, cancellations, and queries.
 * Implements {@link BookingDisplay} for displaying parking bookings.
 */
public final class ParkingService implements BookingDisplay<ParkingBooking> {

    private final ParkingLotManager parkingLotManager = ParkingLotManager.getInstance();
    private final CustomerManager customerManager = CustomerManager.getInstance();
    private static final ParkingService instance = new ParkingService();

    private ParkingService() {}

    public static ParkingService getInstance() {
        return instance;
    }

    /**
     * Handles creating a new parking reservation for a customer.
     * Prompts the user for customer details, vehicle information, and reservation timing.
     */
    public void handleParkingReservation() {
        String customerName = InputHelper.parseString("Enter customer name: ");
        LocalDate dob = InputHelper.parseDateOfBirth("Enter customer date of birth (YYYY-MM-DD): ");

        String vehicleRegistration = InputHelper.parseString("Enter vehicle registration number: ");

        LocalDateTime startTime = null;
        while (startTime == null || startTime.isBefore(LocalDateTime.now())) {
            startTime = InputHelper.parseDateTime("Enter parking start time (YYYY-MM-DDTHH:MM): ");
            if (startTime.isBefore(LocalDateTime.now())) {
                System.out.println("Start time must be today or later. Please try again.");
            }
        }

        int duration;
        LocalDateTime endTime;
        do {
            duration = InputHelper.parseInt("Enter the parking duration in days: ");
            endTime = startTime.plusDays(duration);
        } while (duration < 1);

        Customer customer = customerManager.getCustomerOrAdd(customerName, dob);

        ParkingSpot availableSpot = parkingLotManager.getAvailableParkingSpotForDateTime(startTime, endTime);

        try {
            ParkingBooking booking = new ParkingBooking(customer, startTime, endTime, vehicleRegistration, availableSpot);
            if (parkingLotManager.addBookingToEntity(availableSpot.getId(), booking)) {
                System.out.println("Parking reservation successful:\n" + booking);
            } else {
                System.out.println("Reservation failed due to a conflict.");
            }
        } catch (BookingDateArgumentException e) {
            System.out.println("Reservation error: " + e.getMessage());
        }
    }

    /**
     * Retrieves all parking bookings from the parking lot manager.
     *
     * @return a list of all parking bookings.
     */
    public List<ParkingBooking> getAllBookings() {
        return parkingLotManager.getAllEntities()
                .stream()
                .flatMap(room -> room.getAllBookings().stream())
                .collect(Collectors.toList());
    }

    /**
     * Handles the removal of a parking reservation for a specific customer.
     */
    public void removeParkingReservation() {
        String customerName = "";
        while (customerName.isBlank()) {
            customerName = InputHelper.parseString("Enter name of customer who made the reservation: ");
            if (customerName.isBlank()) System.out.println("Name cannot be blank");
        }
        LocalDate dob = InputHelper.parseDateOfBirth("Enter customer date of birth (YYYY-MM-DD): ");

        Customer customer = customerManager.getCustomer(customerName, dob);
        List<ParkingBooking> customerBookings = parkingLotManager.getAllBookingsForCustomer(customer);
        if (customerBookings.isEmpty()) {
            System.out.println(ConsoleColour.RED);
            System.out.println("No bookings found for this customer.");
            System.out.println(ConsoleColour.RESET);
            return;
        }

        displayBookings(customerBookings);

        int bookingIndexToRemove = 0;
        while (bookingIndexToRemove <= 0 || bookingIndexToRemove > customerBookings.size()) {
            bookingIndexToRemove = InputHelper.parseInt("Enter the number of the booking to remove: ");
            if (bookingIndexToRemove <= 0 || bookingIndexToRemove > customerBookings.size()) {
                System.out.println("Invalid index. Please try again.");
            }
        }

        ParkingBooking bookingToRemove = customerBookings.get(bookingIndexToRemove - 1);
        parkingLotManager.removeBookingFromEntity(bookingToRemove.getParkingSpot().getId(), bookingToRemove);
        System.out.println("Successfully removed the booking at index " + bookingIndexToRemove);
    }
}

