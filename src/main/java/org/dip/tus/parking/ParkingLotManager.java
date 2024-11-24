package org.dip.tus.parking;

import org.dip.tus.core.AbstractBookingManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ParkingLotManager extends AbstractBookingManager<ParkingSpot, ParkingBooking> {

    private static final ParkingLotManager instance = new ParkingLotManager();
    public static ParkingLotManager getInstance() {
        return instance;
    }
    private ParkingLotManager() {}
    private ArrayList<ParkingSpot> parkingSpotList;

    public ParkingSpot getCurrentAvailableParkingSpot() {
        return parkingSpotList
                .stream()
                .filter(p -> !p.isOccupied(LocalDateTime.now()))
                .findFirst().orElse(null);
    }

    public ParkingSpot getAvailableParkingSpotForDateTime(ParkingBooking parkingBooking) {
        return parkingSpotList
                .stream()
                .filter(p -> !p.doesBookingClash(parkingBooking))
                .findFirst().orElse(null);
    }
    public ArrayList<ParkingSpot> getParkingSpotList() {
        return new ArrayList<>(parkingSpotList);
    }
}
