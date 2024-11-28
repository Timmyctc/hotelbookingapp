package org.dip.tus.core;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * Abstract class representing an entity that manages a collection of its own bookings.
 * Provides functionality to manage bookings, check for booking clashes, and handle booking queues.
 *
 * @param <T> The type of booking associated with this entity.
 */
public abstract class AbstractEntity<T extends AbstractBooking> {

    protected PriorityQueue<T> bookings;

    public abstract String getId();

    /**
     * Checks if a given booking clashes with any existing bookings in the queue.
     *
     * @param booking The booking to check.
     * @return {@code true} if the booking clashes with any existing bookings, {@code false} otherwise.
     */
    public boolean doesBookingClash(T booking) {
        if (bookings.isEmpty()) {
            return false;
        }
        return bookings
                .stream()
                .anyMatch(b -> booking.getBookingDateTimeStart().isBefore(b.getBookingDateTimeEnd()) &&
                        booking.getBookingDateTimeEnd().isAfter(b.getBookingDateTimeStart()));
    }

    /**
     * Checks if a given time range clashes with any existing bookings in the queue.
     *
     * @param start The start of the time range.
     * @param end The end of the time range.
     * @return {@code true} if the time range clashes with any existing bookings, {@code false} otherwise.
     */
    public boolean doesBookingClash(LocalDateTime start, LocalDateTime end) {
        if (bookings.isEmpty()) {
            return false;
        }
        return bookings
                .stream()
                .anyMatch(b -> b.getBookingDateTimeStart().isBefore(end) &&
                        start.isBefore(b.getBookingDateTimeEnd()));
    }

    /**
     * Adds a new booking to the bookings queue. The bookings are automatically ordered based on the comparator
     * defined in the entity's implementation.
     *
     * @param newBooking The new booking to add.
     * @return {@code true} if the booking was successfully added, {@code false} if it clashes with existing bookings.
     */
    public boolean addBookingToQueue(T newBooking) {
        if (!doesBookingClash(newBooking)) {
            bookings.add(newBooking);
            return true;
        }
        return false;
    }

    /**
     * Removes a booking from the bookings queue, if it exists.
     * @param booking The booking to remove.
     * @return {@code true} if the booking was successfully removed, {@code false} otherwise.
     */
    public boolean removeBookingFromQueue(T booking) {
        return bookings.remove(booking);
    }

    /**
     * Checks if the entity is currently occupied based on the current time.
     * @param currentTime The current time.
     * @return {@code true} if the entity is currently occupied, {@code false} otherwise.
     */
    public boolean isOccupied(LocalDateTime currentTime) {
        T nextBooking = getNextBooking();
        if (Objects.nonNull(nextBooking)) {
            return currentTime.isAfter(nextBooking.getBookingDateTimeStart())
                    && currentTime.isBefore(nextBooking.getBookingDateTimeEnd());
        }
        return false;
    }

    /**
     * Retrieves the next booking in the bookings queue. The bookings are ordered chronologically based on their start time.
     * @return The next booking in the queue, or {@code null} if the queue is empty.
     */
    public T getNextBooking() {
        return bookings.peek();
    }

    /**
     * Retrieves a copy of all bookings in the queue.
     * @return A new {@code PriorityQueue} containing all bookings in this entity.
     */
    public PriorityQueue<T> getAllBookings() {
        return new PriorityQueue<>(bookings);
    }
}
