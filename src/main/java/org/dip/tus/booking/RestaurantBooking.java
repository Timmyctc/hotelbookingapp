package org.dip.tus.booking;

import org.dip.tus.Customer;
import org.dip.tus.MealEnum;

import java.sql.Time;
import java.util.Date;

public class RestaurantBooking extends  AbstractBooking {

    private Customer customer;
    private MealEnum mealEnum;

    public RestaurantBooking(MealEnum mealEnum, Customer customer, Date startDate, Time startTime, Date endDate, Time endTime, int roomNumber) {
        super(startDate, startTime, endDate, endTime, roomNumber);
        if(mealEnum.equals(MealEnum.BREAKFAST)) //Check is the time before 11, if lunch check is it after 11 and before 5 if dinner after 5 before 10

    }

    @Override
    public String generateBookingID() {
        return "";
    }
}
