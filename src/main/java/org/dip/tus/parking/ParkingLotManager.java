package org.dip.tus.parking;

import org.dip.tus.booking.ParkingBooking;

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

    public ParkingSpot getAvailableParkingSpotForParkingBooking(ParkingBooking parkingBooking) {
//        Stream.findAny()
        return null;
    }
}
