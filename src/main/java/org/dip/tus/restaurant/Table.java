package org.dip.tus.restaurant;

import org.dip.tus.core.AbstractEntity;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class Table extends AbstractEntity<RestaurantBooking> {

    private final int tableNumber;
    private int seats;

    public Table(int tableNumber, int seats) {
        if (tableNumber < 1 || tableNumber > 100) { // Example: Validate table number range
            throw new IllegalArgumentException("Invalid Table Number");
        }
        this.tableNumber = tableNumber;
        this.seats = seats;
        this.bookings = new PriorityQueue<>(Comparator.comparing(RestaurantBooking::getBookingDateTimeStart));
    }

    public int getTableNumber() {
        return tableNumber;
    }

    @Override
    public String getId() {
        return String.valueOf(tableNumber); // Unique identifier as a string
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
