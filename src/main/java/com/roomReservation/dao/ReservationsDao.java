package com.roomReservation.dao;

import com.roomReservation.model.Reservations;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Repository
public class ReservationsDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void createReservation(Reservations reservations) {
        entityManager.persist(reservations);
    }

    public Collection<Reservations> getReservationByRoomName(String roomName) {
        Query query = entityManager.createQuery("SELECT re FROM Reservations re where re.rooms = :roomName");
        return (Collection<Reservations>) query.getResultList();
    }

    public Collection<Reservations> getReservationsList(Reservations reservations) {
        Query query;
        if (reservations.getDateTo() == null && reservations.getDateFrom() == null) {
            query = entityManager.createQuery("SELECT re FROM Reservations re");
        } else if (reservations.getDateTo() == null) {
            query = entityManager.createQuery("SELECT re FROM Reservations re where re.dateFrom >= :dateFrom")
                    .setParameter("dateFrom", reservations.getDateFrom());
        } else if (reservations.getDateFrom() == null) {
            query = entityManager.createQuery("SELECT re FROM Reservations re where re.dateTo <= :dateTo")
                    .setParameter("dateTo", reservations.getDateTo());
        } else {
            query = entityManager.createQuery("SELECT re FROM Reservations re where re.dateFrom >= :dateFrom and re.dateTo <= :dateTo")
                    .setParameter("dateTo", reservations.getDateTo())
                    .setParameter("dateFrom", reservations.getDateFrom());
        }

        return (Collection<Reservations>) query.getResultList();
    }

    public Collection<Reservations> getReservationsRoom(Reservations reservations) {
        Query query;
        if (reservations.getDateTo() == null && reservations.getDateFrom() == null) {
            query = entityManager.createQuery("SELECT re FROM Reservations re where re.rooms.roomName = :roomName")
                    .setParameter("roomName", reservations.getRooms().getRoomName());
        } else if (reservations.getDateTo() == null) {
            query = entityManager.createQuery("SELECT re FROM Reservations re where re.dateFrom >= :dateFrom and re.rooms.roomName = :roomName")
                    .setParameter("dateFrom", reservations.getDateFrom())
                    .setParameter("roomName", reservations.getRooms().getRoomName());
        } else if (reservations.getDateFrom() == null) {
            query = entityManager.createQuery("SELECT re FROM Reservations re where re.dateTo <= :dateTo and re.rooms.roomName = :roomName")
                    .setParameter("dateTo", reservations.getDateTo())
                    .setParameter("roomName", reservations.getRooms().getRoomName());
        } else {
            query = entityManager.createQuery("SELECT re FROM Reservations re where re.dateFrom >= :dateFrom and re.dateTo <= :dateTo and re.rooms.roomName = :roomName")
                    .setParameter("dateTo", reservations.getDateTo())
                    .setParameter("dateFrom", reservations.getDateFrom())
                    .setParameter("roomName", reservations.getRooms().getRoomName());
        }

        return (Collection<Reservations>) query.getResultList();
    }
}
