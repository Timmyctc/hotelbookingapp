package org.dip.tus.core;

import java.util.List;

/**
 * Interface for managing entities
 *
 * @param <E> the type of entity being managed, extending {@link AbstractEntity}.
 */
public interface BookingManager<E extends AbstractEntity> {

    /**
     * Adds a new entity to the booking manager.
     *
     * @param entity the entity to add.
     */
    void addEntity(E entity);

    /**
     * Retrieves all managed entities.
     *
     * @return a list of all entities.
     */
    List<E> getAllEntities();

    /**
     * Finds an entity by its unique identifier.
     *
     * @param id the identifier of the entity to find.
     * @return the entity with the specified ID, or {@code null} if not found.
     */
    E findEntityById(String id);
}
