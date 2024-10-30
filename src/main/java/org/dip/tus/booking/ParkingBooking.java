package org.dip.tus.booking;

import org.dip.tus.Customer;
import org.dip.tus.parking.ParkingLotManager;
import org.dip.tus.parking.ParkingSpot;

import java.sql.Time;
import java.util.Date;

public class ParkingBooking extends AbstractBooking {

    private Customer customer;
    private String registration;
    private ParkingLotManager parkingLotManager = ParkingLotManager.getInstance();

    public ParkingBooking(Customer customer, String registration, Date startDate, Time startTime, Date endDate, Time endTime, int roomNumber) {
        super(startDate, startTime, endDate, endTime, roomNumber);
        this.customer = customer;
        this.registration = registration;
    }

    public ParkingSpot getAvailableParkingSpot (ParkingBooking parkingBooking) {
        return parkingLotManager.getAvailableSpot();
    }

}
