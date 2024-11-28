package org.dip.tus.parking;

import org.dip.tus.core.AbstractBookingManager;
import org.dip.tus.customer.Customer;
import org.dip.tus.menu.ConsoleColour;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Singleton class Manages parking spots and their associated bookings. Provides functionality to add, retrieve, and display parking
 * spots and their availability.
 */
public class ParkingLotManager extends AbstractBookingManager<ParkingSpot, ParkingBooking> {

    private static final ParkingLotManager instance = new ParkingLotManager();

    /**
     * Returns the singleton instance of the ParkingLotManager.
     * @return The ParkingLotManager instance.
     */
    public static ParkingLotManager getInstance() {
        return instance;
    }

    private ParkingLotManager() {
        initialiseParkingLot();
    }

    /**
     * Initializes the parking lot by adding parking spots from sections A to D and numbers 1 to 5.
     */
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
     * @param start The booking start time for which availability is being checked.
     * @param end   The booking end time for which availability is being checked.
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
     * Displays the available parking spots in a formatted table.
     *
     * @param availableParkingSpots A list of parking spots that are currently available.
     */
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

    /**
     * Calculates the cost of a parking booking.
     *
     * @param parkingBooking The parking booking for which the cost is to be calculated.
     * @return The total cost of the parking booking.
     */
    public double calculateCostForBooking(ParkingBooking parkingBooking) {
        ParkingSpot parkingSpot = parkingBooking.getParkingSpot();
        return parkingSpot.calculateCost(parkingBooking.getBookingDateTimeStart(), parkingBooking.getBookingDateTimeEnd());
    }

    /**
     * Retrieves all bookings made by a specific customer across all parking spots.
     *
     * @param customer The customer whose bookings are to be retrieved.
     * @return A list of parking bookings made by the specified customer.
     */
    @Override
    public List<ParkingBooking> getAllBookingsForCustomer(Customer customer) {
        return getAllEntities()
                .stream()
                .flatMap(room -> room.getAllBookings().stream())
                .filter(booking -> booking.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }
}
