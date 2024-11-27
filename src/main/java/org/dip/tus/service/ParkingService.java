package org.dip.tus.service;

import org.dip.tus.customer.Customer;
import org.dip.tus.customer.CustomerManager;
import org.dip.tus.exception.BookingDateArgumentException;
import org.dip.tus.parking.ParkingBooking;
import org.dip.tus.parking.ParkingLotManager;
import org.dip.tus.parking.ParkingSpot;
import org.dip.tus.util.InputHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public final class ParkingService {

    private final ParkingLotManager parkingLotManager = ParkingLotManager.getInstance();
    private final CustomerManager customerManager = CustomerManager.getInstance();
    private static final ParkingService instance = new ParkingService();

    private ParkingService() {}

    public static ParkingService getInstance() {
        return instance;
    }

    public void handleParkingReservation() {
        String customerName = InputHelper.parseString("Enter customer name: ");
        LocalDate dob = InputHelper.parseDate("Enter customer date of birth (YYYY-MM-DD): ");

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

    public List<ParkingBooking> getAllBookings() {
        return parkingLotManager.getAllEntities()
                .stream()
                .flatMap(room -> room.getAllBookings().stream())
                .collect(Collectors.toList());
    }

    public void removeParkingReservation() {
    }
}

