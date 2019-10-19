package com.roomReservation.service;

import com.roomReservation.model.Rooms;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomsService {

    public void createRoom(Rooms rooms);

    public void deleteRoom(String roomName);

    public void updateRoom(Rooms rooms);

    public List<Rooms> getRooms();
}
