package org.dip.tus.restaurant;

import org.dip.tus.customer.Customer;
import org.dip.tus.core.AbstractBooking;
import org.dip.tus.exception.BookingDateArgumentException;

import java.time.LocalDateTime;

public final class RestaurantBooking extends AbstractBooking {

    private final String restaurantBookingID;
    private final MealEnum mealType;

    public RestaurantBooking(Customer customer, LocalDateTime startTime, LocalDateTime endTime, int roomNumber) throws BookingDateArgumentException {
        super(customer, roomNumber, startTime, endTime);
        this.mealType = determineMealType(startTime);
        this.restaurantBookingID = generateBookingID();

    }

    private MealEnum determineMealType(LocalDateTime startTime) {
        int hour = startTime.getHour();
        if (hour < 12) {
            return MealEnum.BREAKFAST;
        } else if (hour < 17) {
            return MealEnum.LUNCH;
        } else {
            return MealEnum.DINNER;
        }
    }

    public MealEnum getMealType() {
        return mealType;
    }

    @Override
    public String generateBookingID() {
        return switch (mealType) {
            case BREAKFAST -> "B" + this.hashCode();
            case LUNCH -> "L" + this.hashCode();
            case DINNER -> "D" + this.hashCode();
        };
    }

    @Override
    public String toString() {
        return "RestaurantBooking{" +
                "restaurantBookingID='" + restaurantBookingID + '\'' +
                ", mealType=" + mealType +
                ", startTime=" + getBookingDateTimeStart() +
                ", endTime=" + getBookingDateTimeEnd() +
                ", tableNumber=" + getRoomNumber() +
                '}';
    }
}
