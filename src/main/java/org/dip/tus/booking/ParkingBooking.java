package org.dip.tus.booking;

import org.dip.tus.Customer;
import org.dip.tus.manager.ParkingLotManager;
import org.dip.tus.entity.ParkingSpot;

import java.time.LocalDateTime;

public class ParkingBooking extends AbstractBooking {

    private String registration;
    private LocalDateTime bookingDateTimeEnd;
    private ParkingLotManager parkingLotManager = ParkingLotManager.getInstance();
    private String parkingBookingID;

    public ParkingBooking(Customer customer, int roomNumber, LocalDateTime startDateTime,
                          LocalDateTime endDateTime, String registration) {
        super(customer, roomNumber, startDateTime);
        this.registration = registration;
        this.bookingDateTimeEnd = endDateTime;
        this.parkingBookingID = generateBookingID();
    }

    public ParkingSpot getAvailableParkingSpotForDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return parkingLotManager.getAvailableParkingSpotForDateTime(startDateTime, endDateTime);
    }

    public LocalDateTime getBookingDateTimeEnd() {
        return bookingDateTimeEnd;
    }

    @Override
    public String generateBookingID() {
        return new StringBuilder()
                .append("P")
                .append(getCustomer().hashCode() + getRoomNumber() + getBookingDateTimeStart().hashCode())
                .toString();
    }
}

