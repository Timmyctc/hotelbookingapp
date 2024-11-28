package org.dip.tus.restaurant;

import org.dip.tus.customer.Customer;
import org.dip.tus.core.AbstractBooking;
import org.dip.tus.exception.BookingDateArgumentException;
import org.dip.tus.menu.ConsoleColour;

import java.time.LocalDateTime;

/**
 * Represents a restaurant booking made by a customer.
 * booking includes details such as the meal type, table, cost, and booking times.
 */
public final class RestaurantBooking extends AbstractBooking {

    private final String restaurantBookingID;
    private final MealEnum mealType;
    private final Table table;
    private final double cost;
    private int numberOfPeople;

    /**
     * Constructs a new RestaurantBooking.
     *
     * @param customer       The customer making the booking.
     * @param startTime      The start time of the booking.
     * @param endTime        The end time of the booking.
     * @param table          The table reserved for the booking.
     * @param numberOfPeople The number of people for the booking.
     * @throws BookingDateArgumentException If the booking start time is after or equal to the end time.
     */
    public RestaurantBooking(Customer customer, LocalDateTime startTime, LocalDateTime endTime, Table table, int numberOfPeople) throws BookingDateArgumentException {
        super(customer, startTime, endTime);
        this.mealType = determineMealType(startTime);
        this.restaurantBookingID = generateBookingID();
        this.table = table;
        this.numberOfPeople = numberOfPeople;
        this.cost = calculateCost();
    }

    /**
     * Calculates the total cost of the booking based on the meal type and number of people.
     *
     * @return The total cost of the booking.
     */
    public double calculateCost() {
        double rate = switch (getMealType()) {
            case BREAKFAST -> 20.0;
            case LUNCH -> 30.0;
            case DINNER -> 40.0;
        };
        return rate * getNumberOfPeople();
    }

    /**
     * Determines the type of meal (e.g., Breakfast, Lunch, Dinner) based on the booking start time.
     *
     * @param startTime The start time of the booking.
     * @return The meal type associated with the booking.
     */
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

    /**
     * Generates a unique booking ID based on the meal type and object hash.
     *
     * @return A unique booking ID.
     */
    @Override
    public String generateBookingID() {
        return switch (mealType) {
            case BREAKFAST -> "B" + this.hashCode();
            case LUNCH -> "L" + this.hashCode();
            case DINNER -> "D" + this.hashCode();
        };
    }

    public MealEnum getMealType() {
        return mealType;
    }

    public Table getTable() {
        return table;
    }

    public double getCost() {
        return cost;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
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
                        ConsoleColour.RED + "Booking ID: " + ConsoleColour.RESET + "%s\n" +
                        ConsoleColour.WHITE_BOLD + "Cost: €" + ConsoleColour.RESET + "%.2f",
                getCustomer().getName(),
                getMealType(),
                getBookingDateTimeStart(),
                getBookingDateTimeEnd(),
                table.getTableNumber(),
                restaurantBookingID,
                getCost()
        );
    }
}
