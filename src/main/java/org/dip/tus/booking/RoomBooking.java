package org.dip.tus.booking;

import org.dip.tus.Customer;

import java.sql.Time;
import java.util.Date;

public class RoomBooking extends AbstractBooking {

    private Customer customer;

    public RoomBooking(Customer customer, Date startDate, Time startTime, Date endDate, Time endTime, int roomNumber) {
        super(startDate, startTime, endDate, endTime, roomNumber);
        this.customer = customer;
    }

}
