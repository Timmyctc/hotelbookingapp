package org.dip.tus.booking;

import org.dip.tus.Customer;
import org.dip.tus.MealEnum;
import org.dip.tus.manager.RestaurantManager;

import java.time.LocalDateTime;

public final class RestaurantBooking extends  AbstractBooking {

    private String restaurantBookingID;
    private MealEnum mealType;
    private RestaurantManager restaurantManager;

    public RestaurantBooking(Customer customer, LocalDateTime startTime,int roomNumber) {
        super(customer,roomNumber,startTime);
        this.restaurantBookingID = generateBookingID();
        this.mealType = getMealTypeFromBookingInfo();
    }

    private MealEnum getMealTypeFromBookingInfo() {
        if (super.getBookingDateTimeStart().getHour() < 12) {
            return MealEnum.BREAKFAST;
        } else if (super.getBookingDateTimeStart().getHour() < 17) {
            return MealEnum.LUNCH;
        } else return MealEnum.DINNER;
    }

    @Override
    public String generateBookingID() {
        return switch (getMealTypeFromBookingInfo()) {
            case BREAKFAST -> "B" + this.hashCode();
            case LUNCH -> "L" + this.hashCode();
            case DINNER -> "D" + this.hashCode();
        };

    }

    @Override
    public boolean doesBookingClash() {
        return false;
    }
}
