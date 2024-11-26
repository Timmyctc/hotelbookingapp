package org.dip.tus.parking;

import org.dip.tus.core.AbstractBookingManager;
import org.dip.tus.customer.Customer;
import org.dip.tus.menu.ConsoleColour;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotManager extends AbstractBookingManager<ParkingSpot, ParkingBooking> {

    private static final ParkingLotManager instance = new ParkingLotManager();

    public static ParkingLotManager getInstance() {
        return instance;
    }

    private ParkingLotManager() {
        initialiseParkingLot();
    }

    private void initialiseParkingLot() {
        IntStream.rangeClosed('A', 'D')
                .mapToObj(section -> (char) section)
                .forEach(section ->
                        IntStream.rangeClosed(1, 5)
                                .forEach(number -> addEntity(new ParkingSpot(section, number)))
                );
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
     * @param start The booking starttime for which availability is being checked.
     * @param end the booking endtime for which acvailability is being checked
     * @return A parking spot that does not have conflicting bookings, or null if none are available.
     */
    public ParkingSpot getAvailableParkingSpotForDateTime(LocalDateTime start, LocalDateTime end) {
        return entities
                .stream()
                .filter(p -> !p.doesBookingClash(start, end))
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
        if (section < 'A' || section > 'F') {
            throw new IllegalArgumentException("Invalid parking section. Must be between A and F.");
        }
        if (spotNumber < 1 || spotNumber > 20) {
            throw new IllegalArgumentException("Invalid parking spot number. Must be between 1 and 20.");
        }
        return entities
                .stream()
                .filter(p -> p.getSection() == section && p.getSpotNumber() == spotNumber)
                .findFirst()
                .orElseGet(() -> {
                    ParkingSpot newSpot = new ParkingSpot(section, spotNumber);
                    entities.add(newSpot);
                    addEntity(newSpot);
                    return newSpot;
                });
    }

    /**
     * Retrieves the list of all parking spots.
     *
     * @return A copy of the parking spot list.
     */
    public ArrayList<ParkingSpot> getParkingSpotList() {
        return new ArrayList<>(entities);
    }

    public void displayAvailableParkingSpots(List<ParkingSpot> availableParkingSpots) {
        if (availableParkingSpots.isEmpty()) {
            System.out.println(ConsoleColour.RED + "No parking spots are currently available." + ConsoleColour.RESET);
            return;
        }

        System.out.println(ConsoleColour.BLUE + "+---------------------------------------------------+");
        System.out.println("|               Available Parking Spots             |");
        System.out.println("+---------------------------------------------------+");
        System.out.println("| Section  | Spot Number   | Total Bookings         |");
        System.out.println("+----------+---------------+------------------------+" + ConsoleColour.RESET);

        for (ParkingSpot parkingSpot : availableParkingSpots) {
            ConsoleColour color = (parkingSpot.getSection() == 'A') ? ConsoleColour.GREEN
                    : (parkingSpot.getSection() == 'B') ? ConsoleColour.CYAN
                    : (parkingSpot.getSection() == 'C') ? ConsoleColour.YELLOW
                    : ConsoleColour.PURPLE;

            System.out.printf(color + "| %-8c | %-13d | %-22d |\n" + ConsoleColour.RESET,
                    parkingSpot.getSection(),
                    parkingSpot.getSpotNumber(),
                    parkingSpot.getAllBookings().size());
        }
        System.out.println(ConsoleColour.BLUE + "+---------------------------------------------------+" + ConsoleColour.RESET);
    }

    public double calculateCostForBooking(ParkingBooking parkingBooking) {
        long days = java.time.Duration.between(parkingBooking.getBookingDateTimeStart(), parkingBooking.getBookingDateTimeEnd()).toDays();
        ParkingSpot parkingSpot = parkingBooking.getParkingSpot();
        double baseCost = parkingSpot.getCostPerHour();
        double totalCost = 0;

        LocalDateTime currentTime = parkingBooking.getBookingDateTimeStart();
        for (int i = 0; i < days; i++) {
            double dailyRate = baseCost;
            totalCost += dailyRate;
            currentTime = currentTime.plusDays(1);
        }
        return totalCost;
    }

    @Override
    public List<ParkingBooking> getAllBookingsForCustomer(Customer customer) {
        return List.of();
    }
}
