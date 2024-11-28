package org.dip.tus.restaurant;

import org.dip.tus.core.AbstractEntity;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * Represents a table in the restaurant.
 */
public class Table extends AbstractEntity<RestaurantBooking> {

    private final int tableNumber;
    private int seats;

    /**
     * Constructs a Table object with a table number and a specified number of seats.
     *
     * @param tableNumber the table number (must be between 1 and 20).
     * @param seats       the number of seats at the table.
     * @throws IllegalArgumentException if the table number is not within the valid range.
     */
    public Table(int tableNumber, int seats) {
        if (tableNumber < 1 || tableNumber > 20) {
            throw new IllegalArgumentException("Invalid Table Number");
        }
        this.tableNumber = tableNumber;
        this.seats = seats;
        this.bookings = new PriorityQueue<>(Comparator.comparing(RestaurantBooking::getBookingDateTimeStart));
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getNumberofSeats() {
        return seats;
    }

    /**
     * Gets the ID of the table, which is its table number as a string.
     *
     * @return the table ID.
     */
    @Override
    public String getId() {
        return String.valueOf(this.tableNumber);
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableNumber=" + tableNumber +
                ", totalBookings=" + bookings.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return tableNumber == table.tableNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableNumber);
    }
}
