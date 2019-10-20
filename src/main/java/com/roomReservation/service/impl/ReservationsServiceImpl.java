package com.roomReservation.service.impl;

import com.roomReservation.dao.ReservationsDao;
import com.roomReservation.model.Reservations;
import com.roomReservation.service.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationsServiceImpl implements ReservationsService {
    private ReservationsDao reservationsDao;

    @Autowired
    public void setRoomsDao(ReservationsDao reservationsDao) {
        this.reservationsDao = reservationsDao;
    }

    @Transactional
    public void createReservation(Reservations reservations) {
        reservationsDao.createReservation(reservations);
    }

    @Transactional
    public List<Reservations> getReservationsList(Reservations reservations) {
        return (List<Reservations>) reservationsDao.getReservationsList(reservations);
    }
}
