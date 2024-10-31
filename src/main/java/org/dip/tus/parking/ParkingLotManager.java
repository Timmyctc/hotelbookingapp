package org.dip.tus.parking;

import org.dip.tus.booking.ParkingBooking;

import java.time.LocalDateTime;

public class ParkingLotManager {

    private static final ParkingLotManager instance = new ParkingLotManager();

    private ParkingLotManager() {}

    public static ParkingLotManager getInstance() {
        return instance;
    }

    public ParkingSpot getCurrentAvailableParkingSpot() {
//        Stream.findFirst()
        return null;
    }

    public ParkingSpot getAvailableParkingSpotForDateTime(LocalDateTime startTime, LocalDateTime endTime) {
//        Stream.findAny()
        return null;
    }
}
