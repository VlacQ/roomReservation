package com.roomReservation.service;

import com.roomReservation.model.Reservations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservationsService {

    public void createReservation(Reservations reservations);

    public List<Reservations> getReservationsList(Reservations reservations);

    public List<Reservations> getReservationsRoom(Reservations reservations);

    public List<Reservations> getReservationsEmployee(Reservations reservations);
}
