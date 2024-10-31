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
    private ParkingSpot allocatedSpot;

    public ParkingBooking(Customer customer,int roomNumber, LocalDateTime startDateTime, LocalDateTime endDateTime, String registration) {
        super(customer, roomNumber, startDateTime);
        this.registration = registration;
        this.bookingDateTimeEnd = endDateTime;
        this.allocatedSpot = getAvailableParkingSpotForDateTime(startDateTime,endDateTime);
    }

    public ParkingSpot getAvailableParkingSpotForDateTime (LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return parkingLotManager.getAvailableParkingSpotForDateTime(startDateTime,endDateTime);
    }

}

