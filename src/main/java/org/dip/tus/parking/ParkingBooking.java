package org.dip.tus.parking;

import org.dip.tus.customer.Customer;
import org.dip.tus.core.AbstractBooking;
import org.dip.tus.exception.BookingDateArgumentException;
import org.dip.tus.menu.ConsoleColour;

import java.time.LocalDateTime;

public final class ParkingBooking extends AbstractBooking {

    private String registration;
    private ParkingLotManager parkingLotManager = ParkingLotManager.getInstance();
    private String parkingBookingID;
    private ParkingSpot parkingSpot;
    private double cost;

    public ParkingBooking(Customer customer, LocalDateTime startDateTime,
                          LocalDateTime endDateTime, String registration, ParkingSpot parkingSpot) throws BookingDateArgumentException {
        super(customer, startDateTime, endDateTime);
        this.registration = registration;
        this.parkingBookingID = generateBookingID();
        this.parkingSpot=parkingSpot;
        this.cost = calculateCost();
    }

    private double calculateCost() {
        return getParkingSpot().calculateCost(getBookingDateTimeStart(), getBookingDateTimeEnd());
    }

    public double getCost() {
        return cost;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    @Override
    public String generateBookingID() {
        return new StringBuilder()
                .append("P")
                .append(getCustomer().hashCode()  + getBookingDateTimeStart().hashCode())
                .toString();
    }
    @Override
    public String toString() {
        return String.format(
                ConsoleColour.PURPLE + "Parking Booking Details:\n" + ConsoleColour.RESET +
                        ConsoleColour.GREEN + "Customer: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.YELLOW + "Vehicle Registration: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.PURPLE + "Parking Spot: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.CYAN + "Booking Start: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.CYAN + "Booking End: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.RED + "Booking ID: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.WHITE + "Cost: €" + ConsoleColour.RESET + "%.2f\n",
                getCustomer().getName(),
                registration,
                parkingSpot.getId(),
                getBookingDateTimeStart(),
                getBookingDateTimeEnd(),
                parkingBookingID,
                getCost()
        );
    }


}

