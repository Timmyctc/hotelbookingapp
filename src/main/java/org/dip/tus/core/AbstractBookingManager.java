package org.dip.tus.core;

import org.dip.tus.customer.Customer;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class providing the core functionality for managing various entities and bookings.
 *
 * @param <E> The type of the entity being managed.
 * @param <B> The type of booking associated with the entity.
 */
public abstract class AbstractBookingManager<E extends AbstractEntity<B>, B extends AbstractBooking> implements BookingManager<E> {

    protected final List<E> entities = new ArrayList<>();

    /**
     * Retrieves all bookings associated with a given customer.
     *
     * @param customer The customer whose bookings are to be retrieved.
     * @return A list of bookings associated with the specified customer.
     */
    public abstract List<B> getAllBookingsForCustomer(Customer customer);

    /**
     * Checks if the given date falls on a weekend.
     *
     * @param date The date to check.
     * @return {@code true} if the date is a Saturday or Sunday, otherwise {@code false}.
     */
    public boolean isWeekend(LocalDateTime date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    /**
     * Adds a booking to a specific entity by its ID.
     *
     * @param entityId The ID of the entity to which the booking is to be added.
     * @param booking The booking to add.
     * @return {@code true} if the booking was successfully added, {@code false} if it conflicts with an existing booking.
     * @throws IllegalArgumentException If no entity with the given ID is found.
     */
    public boolean addBookingToEntity(String entityId, B booking) {
        E entity = findEntityById(entityId);
        if (entity == null) {
            throw new IllegalArgumentException("Entity with ID " + entityId + " not found.");
        }
        return entity.doesBookingClash(booking) ? false : entity.addBookingToQueue(booking);
    }

    /**
     * Removes a booking from a specific entity by its ID.
     *
     * @param entityId The ID of the entity from which the booking is to be removed.
     * @param booking The booking to remove.
     * @return {@code true} if the booking was successfully removed, {@code false} otherwise.
     * @throws IllegalArgumentException If no entity with the given ID is found.
     */
    public boolean removeBookingFromEntity(String entityId, B booking) {
        E entity = findEntityById(entityId);
        if (entity == null) {
            throw new IllegalArgumentException("Entity with ID " + entityId + " not found.");
        }
        return entity.removeBookingFromQueue(booking);
    }

    /**
     * Adds a new entity to the list of managed entities.
     *
     * @param entity The entity to add.
     * @throws IllegalArgumentException If an entity with the same ID already exists.
     */
    @Override
    public void addEntity(E entity) {
        if (findEntityById(entity.getId()) != null) {
            throw new IllegalArgumentException("Entity with the same ID already exists.");
        }
        entities.add(entity);
    }

    /**
     * Retrieves an unmodifiable list of all managed entities.
     *
     * @return A list of all entities managed by this booking manager.
     */
    @Override
    public List<E> getAllEntities() {
        return List.copyOf(entities);
    }

    /**
     * Finds an entity by its unique ID.
     *
     * @param id The unique ID of the entity to find.
     * @return The entity with the specified ID, or {@code null} if no such entity exists.
     */
    @Override
    public E findEntityById(String id) {
        return entities.stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
