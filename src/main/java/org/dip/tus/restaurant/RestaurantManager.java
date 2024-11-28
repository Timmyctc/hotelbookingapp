package org.dip.tus.restaurant;

import org.dip.tus.core.AbstractBookingManager;
import org.dip.tus.customer.Customer;
import org.dip.tus.menu.ConsoleColour;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Singleton Manages restaurant tables and their bookings.
 * Provides functionality for initializing tables, displaying available tables,
 * and retrieving bookings for a specific customer.
 */
public class RestaurantManager extends AbstractBookingManager<Table, RestaurantBooking> {

    private static final RestaurantManager instance = new RestaurantManager();

    private RestaurantManager() {
        initialiseEntities();
    }

    public static RestaurantManager getInstance() {
        return instance;
    }

    /**
     * Initializes restaurant tables with predefined capacities.
     * Tables are categorized by the number of seats and assigned a range of table numbers.
     * 1-5 seat 2, 5-10 seat 4, 10-15 seat 5 and 15-20 seat 10
     */
    private void initialiseEntities() {
        IntStream.rangeClosed(1, 5).forEach(i -> addEntity(new Table(i, 2)));
        IntStream.rangeClosed(6, 10).forEach(i -> addEntity(new Table(i, 4)));
        IntStream.rangeClosed(11, 15).forEach(i -> addEntity(new Table(i, 5)));
        IntStream.rangeClosed(16, 20).forEach(i -> addEntity(new Table(i, 10)));
    }

    /**
     * Displays a formatted table of available tables in the restaurant.
     *
     * @param availableTables A list of tables that are currently available for booking.
     */
    public void displayAvailableTables(List<Table> availableTables) {
        if (availableTables.isEmpty()) {
            System.out.println(ConsoleColour.RED + "No tables are currently available." + ConsoleColour.RESET);
            return;
        }

        System.out.println(ConsoleColour.BLUE + "+---------------------------------------------------+");
        System.out.println("|                 Available Tables                  |");
        System.out.println("+---------------------------------------------------+");
        System.out.println("| Table Number | Seats        | Total Bookings      |");
        System.out.println("+--------------+--------------+---------------------+" + ConsoleColour.RESET);

        for (Table table : availableTables) {
            ConsoleColour color = (table.getNumberofSeats() <= 2) ? ConsoleColour.GREEN
                    : (table.getNumberofSeats() <= 4) ? ConsoleColour.CYAN
                    : (table.getNumberofSeats() <= 6) ? ConsoleColour.YELLOW
                    : ConsoleColour.PURPLE;

            System.out.printf(color + "| %-12d | %-12d | %-20d |\n" + ConsoleColour.RESET,
                    table.getTableNumber(),
                    table.getNumberofSeats(),
                    table.getAllBookings().size());
        }
        System.out.println(ConsoleColour.BLUE + "+---------------------------------------------------+" + ConsoleColour.RESET);
    }

    /**
     * Retrieves all bookings made by a specific customer.
     *
     * @param customer The customer whose bookings are to be retrieved.
     * @return A list of bookings associated with the given customer.
     */
    public List<RestaurantBooking> getAllBookingsForCustomer(Customer customer) {
        return getAllEntities()
                .stream()
                .flatMap(room -> room.getAllBookings().stream())
                .filter(booking -> booking.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }
}
