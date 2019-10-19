package com.roomReservation.dao;

import com.roomReservation.model.Rooms;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Repository
public class RoomsDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void createRoom(Rooms rooms){
        entityManager.persist(rooms);
    }

    public void updateRoom(Rooms rooms){
        entityManager.merge(rooms);
    }

    public Rooms getRoomByName(String roomName){
        return entityManager.find(Rooms.class, roomName);
    }

    public Collection<Rooms> getRooms(){
        Query query = entityManager.createQuery("SELECT e FROM Rooms e");
        return (Collection<Rooms>) query.getResultList();
    }

    public void deleteRoom(String roomName){
        Rooms rooms = getRoomByName(roomName);
        if (rooms != null)
            entityManager.remove(rooms);
    }
}
