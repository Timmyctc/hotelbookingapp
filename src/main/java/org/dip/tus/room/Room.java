package org.dip.tus.room;

import org.dip.tus.core.AbstractEntity;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * Represents a hotel room that can be booked.
 */
public class Room extends AbstractEntity<RoomBooking> {

    private final int roomNumber;
    private final RoomType roomType;

    /**
     * Constructs a Room object with the specified room number and type.
     * initialises the priority Queue with a comparator that compares bookings based on the start to ensure
     * chronological order
     *
     * @param roomNumber the room number (must be between 1 and 100).
     * @param roomType   the type of the room (SINGLE, DOUBLE, QUEEN, KING).
     * @throws IllegalArgumentException if the room number is not within the valid range.
     */
    protected Room(int roomNumber, RoomType roomType) {
        if (roomNumber < 1 || roomNumber > 100) {
            throw new IllegalArgumentException("Invalid Room Number");
        }
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.bookings = new PriorityQueue<>(Comparator.comparing(RoomBooking::getBookingDateTimeStart));
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Retrieves the room type.
     *
     * @return the room type (SINGLE, DOUBLE, QUEEN, KING).
     */
    public RoomType getRoomType() {
        return roomType;
    }

    /**
     * Gets the base cost of the room based on its type.
     *
     * @return the base cost of the room.
     */
    public double getBaseCost() {
        return switch (getRoomType()) {
            case SINGLE -> 80.0;
            case DOUBLE -> 100.0;
            case QUEEN -> 120.0;
            case KING -> 150.0;
        };
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                ", totalBookings=" + bookings.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

    @Override
    public String getId() {
        return String.valueOf(getRoomNumber());
    }
}
