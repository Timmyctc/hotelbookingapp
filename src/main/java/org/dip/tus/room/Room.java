package org.dip.tus.room;

import org.dip.tus.core.AbstractEntity;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class Room extends AbstractEntity<RoomBooking> {

    private final int roomNumber;
    private final RoomType roomType;

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

    public RoomType getRoomType() {
        return roomType;
    }

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
