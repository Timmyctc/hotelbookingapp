package org.dip.tus.core;

import java.util.List;

public interface BookingManager<E extends AbstractEntity> {
    void addEntity(E entity);
    boolean removeEntity(E entity);
    List<E> getAllEntities();
    E findEntityById(String id); // Generic method for locating entities by their unique ID
}
