package org.dip.tus.booking;

import org.dip.tus.Customer;

import java.sql.Time;
import java.util.Date;

public class RestaurantBooking extends  AbstractBooking {

    private Customer customer;

    public RestaurantBooking(Date startDate, Time startTime, Date endDate, Time endTime, int roomNumber) {
        super(startDate, startTime, endDate, endTime, roomNumber);
    }
}
