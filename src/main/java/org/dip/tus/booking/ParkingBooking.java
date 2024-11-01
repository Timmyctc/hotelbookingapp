package org.dip.tus.booking;

import org.dip.tus.Customer;
import org.dip.tus.parking.ParkingLotManager;
import org.dip.tus.parking.ParkingSpot;

import java.time.LocalDateTime;

public class ParkingBooking extends AbstractBooking {

    private Customer customer;
    private String registration;
    private LocalDateTime bookingDateTimeEnd;
    private ParkingLotManager parkingLotManager = ParkingLotManager.getInstance();

    public ParkingBooking(Customer customer, int roomNumber, LocalDateTime startDateTime,
                          LocalDateTime endDateTime, String registration, String bookingID) {
        super(customer, roomNumber, startDateTime, bookingID);
        this.registration = registration;
        this.bookingDateTimeEnd = endDateTime;
    }

    public ParkingSpot getAvailableParkingSpotForDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return parkingLotManager.getAvailableParkingSpotForDateTime(startDateTime, endDateTime);
    }

    public LocalDateTime getBookingDateTimeEnd() {
        return bookingDateTimeEnd;
    }

    public String generateBookingID() {
        return new StringBuilder()
                .append("P")
                .append(getCustomer().hashCode() + getRoomNumber() + getBookingDateTimeStart().hashCode())
                .toString();
    }
}

