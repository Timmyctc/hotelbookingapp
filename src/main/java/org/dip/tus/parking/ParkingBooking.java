package org.dip.tus.parking;

import org.dip.tus.customer.Customer;
import org.dip.tus.core.AbstractBooking;
import org.dip.tus.exception.BookingDateArgumentException;

import java.time.LocalDateTime;

public final class ParkingBooking extends AbstractBooking {

    private String registration;
    private ParkingLotManager parkingLotManager = ParkingLotManager.getInstance();
    private String parkingBookingID;

    public ParkingBooking(Customer customer, int roomNumber, LocalDateTime startDateTime,
                          LocalDateTime endDateTime, String registration) throws BookingDateArgumentException {
        super(customer, roomNumber, startDateTime, endDateTime);
        this.registration = registration;
        this.parkingBookingID = generateBookingID();
    }

    public ParkingSpot getAvailableParkingSpotForDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return parkingLotManager.getAvailableParkingSpotForDateTime(this);
    }

    @Override
    public String generateBookingID() {
        return new StringBuilder()
                .append("P")
                .append(getCustomer().hashCode() + getRoomNumber() + getBookingDateTimeStart().hashCode())
                .toString();
    }
}

