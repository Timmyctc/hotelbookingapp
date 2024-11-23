package org.dip.tus.entity;

import org.dip.tus.booking.RoomBooking;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class Room extends AbstractEntity<RoomBooking> {

    private int roomNumber;

    public Room(int roomNumber) {
        if (roomNumber < 1 || roomNumber > 42) {
            throw new IllegalArgumentException("Invalid Room Number");
        }
        this.roomNumber = roomNumber;
        this.bookings = new PriorityQueue<>(Comparator.comparing(RoomBooking::getBookingDateTimeStart));
    }

    @Override
    public boolean doesBookingClash(RoomBooking newBooking) {
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
        return "Room{" +
                "roomNumber=" + roomNumber +
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
}
