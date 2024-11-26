package org.dip.tus.restaurant;

import org.dip.tus.customer.Customer;
import org.dip.tus.core.AbstractBooking;
import org.dip.tus.exception.BookingDateArgumentException;
import org.dip.tus.menu.ConsoleColour;

import java.time.LocalDateTime;

public final class RestaurantBooking extends AbstractBooking {

    private final String restaurantBookingID;
    private final MealEnum mealType;
    private final Table table;

    public RestaurantBooking(Customer customer, LocalDateTime startTime, LocalDateTime endTime, Table table) throws BookingDateArgumentException {
        super(customer, startTime, endTime);
        this.mealType = determineMealType(startTime);
        this.restaurantBookingID = generateBookingID();
        this.table = table;
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
        return String.format(
                ConsoleColour.BLUE + "Restaurant Booking Details:\n" + ConsoleColour.RESET +
                        ConsoleColour.GREEN + "Customer: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.CYAN + "Meal Type: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.YELLOW + "Start Time: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.YELLOW + "End Time: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.PURPLE + "Table Number: " + ConsoleColour.RESET + "%d\n" +
                        ConsoleColour.RED + "Booking ID: " + ConsoleColour.RESET + "%s\n",
                getCustomer().getName(),
                getMealType(),
                getBookingDateTimeStart(),
                getBookingDateTimeEnd(),
                table.getTableNumber(),
                restaurantBookingID
        );
    }

}
