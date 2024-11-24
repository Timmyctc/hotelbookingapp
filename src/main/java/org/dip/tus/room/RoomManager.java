package org.dip.tus.room;

import org.dip.tus.core.AbstractBookingManager;

import java.util.HashMap;
import java.util.Map;

public class RoomManager extends AbstractBookingManager<Room, RoomBooking> {

    private static final RoomManager instance = new RoomManager();
    private final Map<Integer, Room> roomRegistry = new HashMap<>();

    private RoomManager() {}

    public static RoomManager getInstance() {
        return instance;
    }

    /**
     * Creates or retrieves a room by its number and type.
     *
     * @param roomNumber the unique room number
     * @param roomType   the type of the room
     * @return the Room instance
     */
    public Room getOrCreateRoom(int roomNumber, RoomType roomType) {
        synchronized (roomRegistry) {
            return roomRegistry.computeIfAbsent(roomNumber, id -> {
                Room newRoom = new Room(roomNumber, roomType);
                addEntity(newRoom);
                return newRoom;
            });
        }
    }

}
