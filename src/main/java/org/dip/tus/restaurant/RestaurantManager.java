package org.dip.tus.restaurant;

import org.dip.tus.core.AbstractBookingManager;

import java.util.HashMap;
import java.util.Map;

public class RestaurantManager extends AbstractBookingManager<Table, RestaurantBooking> {

    private static final RestaurantManager instance = new RestaurantManager();
    private final Map<Integer, Table> tableRegistry = new HashMap<>();

    private RestaurantManager() {}

    public static RestaurantManager getInstance() {
        return instance;
    }

    /**
     * Creates or retrieves a table by its number.
     *
     * @param tableNumber the unique table number
     * @return the Table instance
     */
    public Table getOrCreateTable(int tableNumber, int seats) {
        synchronized (tableRegistry) {
            return tableRegistry.computeIfAbsent(tableNumber, id -> {
                Table newTable = new Table(tableNumber, seats);
                addEntity(newTable);
                return newTable;
            });
        }
    }
    @Override
    public Table findEntityById(String id) {
        return tableRegistry.get(Integer.parseInt(id));
    }
}
