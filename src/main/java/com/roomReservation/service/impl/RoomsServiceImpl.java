package com.roomReservation.service.impl;

import com.roomReservation.dao.RoomsDao;
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
        roomsDao.createRoom(rooms);
    }

    @Transactional
    public void deleteRoom(String roomName) {
        roomsDao.deleteRoom(roomName);
    }

    @Transactional
    public void updateRoom(Rooms rooms) {
        roomsDao.updateRoom(rooms);
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
