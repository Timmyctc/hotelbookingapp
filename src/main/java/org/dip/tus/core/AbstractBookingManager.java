package org.dip.tus.core;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBookingManager<E extends AbstractEntity<B>, B extends AbstractBooking> implements BookingManager<E> {

    protected final List<E> entities = new ArrayList<>();

    @Override
    public void addEntity(E entity) {
        if (findEntityById(entity.getId()) != null) {
            throw new IllegalArgumentException("Entity with the same ID already exists.");
        }
        entities.add(entity);
    }

    @Override
    public boolean removeEntity(E entity) {
        return entities.remove(entity);
    }

    @Override
    public List<E> getAllEntities() {
        return List.copyOf(entities);
    }

    @Override
    public E findEntityById(String id) {
        return entities.stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean addBookingToEntity(String entityId, B booking) {
        E entity = findEntityById(entityId);
        if (entity == null) {
            throw new IllegalArgumentException("Entity with ID " + entityId + " not found.");
        }
        return entity.doesBookingClash(booking) ? false : entity.addBookingToQueue(booking);
    }

    public boolean removeBookingFromEntity(String entityId, B booking) {
        E entity = findEntityById(entityId);
        if (entity == null) {
            throw new IllegalArgumentException("Entity with ID " + entityId + " not found.");
        }
        return entity.removeBookingFromQueue(booking);
    }
}
