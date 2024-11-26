package org.dip.tus.parking;

import org.dip.tus.core.AbstractBookingManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ParkingLotManager extends AbstractBookingManager<ParkingSpot, ParkingBooking> {

    private static final ParkingLotManager instance = new ParkingLotManager();

    public static ParkingLotManager getInstance() {
        return instance;
    }

    private ParkingLotManager() {
        initialiseParkingLot();
    }

    private void initialiseParkingLot() {

    }

    /**
     * Retrieves the first currently available parking spot.
     *
     * @return A parking spot that is not currently occupied or null if none are available.
     */
    public ParkingSpot getCurrentAvailableParkingSpot() {
        return entities
                .stream()
                .filter(p -> !p.isOccupied(LocalDateTime.now()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves a parking spot available for a specific booking time range.
     *
     * @param parkingBooking The booking for which availability is being checked.
     * @return A parking spot that does not have conflicting bookings, or null if none are available.
     */
    public ParkingSpot getAvailableParkingSpotForDateTime(ParkingBooking parkingBooking) {
        return entities
                .stream()
                .filter(p -> !p.doesBookingClash(parkingBooking))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves or adds a parking spot based on the section and spot number.
     *
     * @param section    The section of the parking spot (e.g., A-F).
     * @param spotNumber The number of the parking spot (e.g., 1-20).
     * @return The existing or newly added parking spot.
     */
    public ParkingSpot getOrCreateParkingSpot(char section, int spotNumber) {
        // Validate the section and spot number inputs
        if (section < 'A' || section > 'F') {
            throw new IllegalArgumentException("Invalid parking section. Must be between A and F.");
        }
        if (spotNumber < 1 || spotNumber > 20) {
            throw new IllegalArgumentException("Invalid parking spot number. Must be between 1 and 20.");
        }

        // Search for an existing parking spot
        return parkingSpotList
                .stream()
                .filter(p -> p.getSection() == section && p.getSpotNumber() == spotNumber)
                .findFirst()
                .orElseGet(() -> {
                    // If not found, create a new parking spot
                    ParkingSpot newSpot = new ParkingSpot(section, spotNumber);
                    parkingSpotList.add(newSpot);
                    addEntity(newSpot); // Add to the manager's list of entities
                    return newSpot;
                });
    }

    /**
     * Retrieves the list of all parking spots.
     *
     * @return A copy of the parking spot list.
     */
    public ArrayList<ParkingSpot> getParkingSpotList() {
        return new ArrayList<>(parkingSpotList);
    }
}
