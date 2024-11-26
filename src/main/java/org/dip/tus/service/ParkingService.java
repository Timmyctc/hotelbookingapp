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

        char section = InputHelper.parseChar("Enter parking spot section (A-F): ", 'A', 'F');

        int spotNumber = InputHelper.parseInt("Enter parking spot number (1-20): ");

        LocalDateTime startTime = InputHelper.parseDateTime("Enter parking start time (YYYY-MM-DDTHH:MM): ");
        int duration = InputHelper.parseInt("Enter the parking duration in hours: ");
        LocalDateTime endTime = startTime.plusHours(duration);

        Customer customer = customerManager.getCustomerOrAdd(customerName, dob);

        ParkingSpot parkingSpot = parkingLotManager.getOrCreateParkingSpot(section, spotNumber);
        if (parkingSpot == null) {
            System.out.println("No available parking spot found.");
            return;
        }

        if (parkingSpot.doesBookingClash(startTime, endTime)) {
            System.out.println("Selected parking spot is already booked for the given time.");
            return;
        }

        try {
            ParkingBooking booking = new ParkingBooking(customer, spotNumber, startTime, endTime, vehicleRegistration);
            if (parkingLotManager.addBookingToEntity(section + String.valueOf(spotNumber), booking)) {
                System.out.println("Parking reservation successful: " + booking);
            } else {
                System.out.println("Reservation failed due to a conflict.");
            }
        } catch (BookingDateArgumentException e) {
            System.out.println("Reservation error: " + e.getMessage());
        }
    }

    public void viewAllParkingSpots() {
        parkingLotManager.getAllEntities().forEach(System.out::println);
    }
}

