package org.dip.tus.manager;

import org.dip.tus.booking.ParkingBooking;
import org.dip.tus.entity.ParkingSpot;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ParkingLotManager implements BookingManager<ParkingSpot>{

    private static final ParkingLotManager instance = new ParkingLotManager();
    public static ParkingLotManager getInstance() {
        return instance;
    }
    private ParkingLotManager() {}
    private ArrayList<ParkingSpot> parkingSpotList;

    public ParkingSpot getCurrentAvailableParkingSpot() {
        return parkingSpotList
                .stream()
                .filter(p -> !p.isOccupied())
                .findFirst().orElse(null);
    }

    public ParkingSpot getAvailableParkingSpotForDateTime(ParkingBooking parkingBooking) {
        return parkingSpotList
                .stream()
                .filter(p -> !p.doesBookingClash(parkingBooking))
                .findFirst().orElse(null);
    }

    @Override
    public boolean addBooking(ParkingSpot booking) {
        return false;
    }

    @Override
    public boolean removeBooking(ParkingSpot booking) {
        return false;
    }

    @Override
    public ParkingSpot getNextBooking() {
        return null;
    }

    public ArrayList<ParkingSpot> getParkingSpotList() {
        return new ArrayList<>(parkingSpotList);
    }
}
