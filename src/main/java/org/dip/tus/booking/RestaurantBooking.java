package org.dip.tus.booking;

import org.dip.tus.Customer;
import org.dip.tus.MealEnum;

import java.time.LocalDateTime;

public class RestaurantBooking extends  AbstractBooking {

    private String restaurantBookingID;
    private MealEnum mealType;

    public RestaurantBooking(Customer customer, LocalDateTime startTime,int roomNumber) {
        super(customer,roomNumber,startTime);
        this.restaurantBookingID = generateBookingID();
        this.mealType = getMealTypeFromBookingInfo();
    }

    private MealEnum getMealTypeFromBookingInfo() {

    }

    @Override
    public String generateBookingID() {
        String prefix;
        switch (getMealTypeFromBookingInfo()) {
            case Breakfast:
                break;
            case Lunch:
                break;
            case Dinner:
                break;
            default:

        return new StringBuilder().append();
    }
}
