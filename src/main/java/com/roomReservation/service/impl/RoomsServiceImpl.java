package com.roomReservation.service.impl;

import com.roomReservation.dao.RoomsDao;
import com.roomReservation.enumerate.Projector;
import com.roomReservation.model.Rooms;
import com.roomReservation.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomsServiceImpl implements RoomsService {
    private RoomsDao roomsDao;

    @Autowired
    public void setRoomsDao(RoomsDao roomsDao) {
        this.roomsDao = roomsDao;
    }

    @Transactional
    public void createRoom(Rooms rooms) {
        if (rooms.getProjector() == null){
            rooms.setProjector(Projector.NO);
        }
        roomsDao.createRoom(rooms);
    }

    @Transactional
    public void deleteRoom(String roomName) {
        roomsDao.deleteRoom(roomName);
    }

    @Transactional
    public void updateRoom(Rooms rooms) {
        Rooms r = getRoomByName(rooms.getRoomName());
        if (rooms.getLocationDescription() != null && !rooms.getLocationDescription().equals("")){
            r.setLocationDescription(rooms.getLocationDescription());
        }
        if (rooms.getNumberOfSeats() != null && !rooms.getNumberOfSeats().toString().equals("")){
            r.setNumberOfSeats(rooms.getNumberOfSeats());
        }
        if (rooms.getProjector() != null && !rooms.getProjector().toString().equals("")){
            r.setProjector(rooms.getProjector());
        }
        if (rooms.getPhoneNumber() != null && !rooms.getPhoneNumber().equals("")){
            r.setPhoneNumber(rooms.getPhoneNumber());
        }
    }

    @Transactional
    public Rooms getRoomByName(String roomName) {
        return roomsDao.getRoomByName(roomName);
    }

    @Transactional
    public List<Rooms> getRooms() {
        return (List<Rooms>) roomsDao.getRooms();
    }
}
