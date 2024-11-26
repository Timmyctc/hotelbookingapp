package org.dip.tus.core;

import org.dip.tus.customer.Customer;
import org.dip.tus.room.RoomBooking;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractBookingManager<E extends AbstractEntity<B>, B extends AbstractBooking> implements BookingManager<E> {

    protected final List<E> entities = new ArrayList<>();

    public abstract List<B> getAllBookingsForCustomer(Customer customer);

    public boolean isWeekend(LocalDateTime date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

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
