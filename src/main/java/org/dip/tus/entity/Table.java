package org.dip.tus.entity;

import org.dip.tus.booking.RestaurantBooking;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class Table extends AbstractEntity<RestaurantBooking> {

    private int tableNumber;

    public Table(int tableNumber) {
        if (tableNumber < 1 || tableNumber > 100) { // Example: Validate table number range
            throw new IllegalArgumentException("Invalid Table Number");
        }
        this.tableNumber = tableNumber;
        this.bookings = new PriorityQueue<>(Comparator.comparing(RestaurantBooking::getBookingDateTimeStart));
    }

    @Override
    public boolean doesBookingClash(RestaurantBooking newBooking) {
        if (bookings.isEmpty()) {
            return false;
        }
        return bookings
                .stream()
                .anyMatch(b -> newBooking.getBookingDateTimeStart().isBefore(b.getBookingDateTimeEnd()) &&
                        newBooking.getBookingDateTimeEnd().isAfter(b.getBookingDateTimeStart()));
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
