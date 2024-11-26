package org.dip.tus.restaurant;

import org.dip.tus.core.AbstractBookingManager;
import org.dip.tus.menu.ConsoleColour;

import java.util.List;
import java.util.stream.IntStream;

public class RestaurantManager extends AbstractBookingManager<Table, RestaurantBooking> {

    private static final RestaurantManager instance = new RestaurantManager();
    private RestaurantManager() {initialiseEntities();}

    public static RestaurantManager getInstance() {
        return instance;
    }

    private void initialiseEntities() {
        IntStream.rangeClosed(1, 5).forEach(i -> addEntity(new Table(i, 2)));
        IntStream.rangeClosed(6, 10).forEach(i -> addEntity(new Table(i, 4)));
        IntStream.rangeClosed(11, 15).forEach(i -> addEntity(new Table(i, 5)));
        IntStream.rangeClosed(16, 20).forEach(i -> addEntity(new Table(i, 10)));
    }

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

}
