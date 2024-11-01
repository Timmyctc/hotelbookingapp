package org.dip.tus.manager;

import org.dip.tus.entity.ParkingSpot;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ParkingLotManager {

    private static final ParkingLotManager instance = new ParkingLotManager();
    public static ParkingLotManager getInstance() {
        return instance;
    }
    private ParkingLotManager() {}
    private ArrayList<ParkingSpot> parkingSpotList;

    public ParkingSpot getCurrentAvailableParkingSpot() {
        return parkingSpotList
                .stream()
                .filter(p -> !p.isSpotOccupied())
                .filter(p -> p.)
    }

    public ParkingSpot getAvailableParkingSpotForDateTime(LocalDateTime startTime, LocalDateTime endTime) {
//        Stream.findAny()
        return null;
    }
}
