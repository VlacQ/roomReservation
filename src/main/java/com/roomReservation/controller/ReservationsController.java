package com.roomReservation.controller;

import com.roomReservation.model.Employees;
import com.roomReservation.model.Reservations;
import com.roomReservation.model.Rooms;
import com.roomReservation.service.EmployeesService;
import com.roomReservation.service.ReservationsService;
import com.roomReservation.service.RoomsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("/reservations")
public class ReservationsController {
    @Autowired
    private ReservationsService reservationsService;
    @Autowired
    private EmployeesService employeesService;
    @Autowired
    private RoomsService roomsService;

    private Map<String, String> response = new HashMap<String, String>();

    private static final Logger logger = LoggerFactory.getLogger(ReservationsController.class);

    @RequestMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Map<String, String> createReservation(@Valid @RequestBody Reservations reservations) {
        logger.info("Called reservations/create");

        if (!checkLoginsAndPasswords(reservations) || !checkRoomNameAndSeats(reservations) || !checkDates(reservations)) {
            response.put("timestamp", String.valueOf(new Date()));
            return response;
        }

        try {
            reservationsService.createReservation(reservations);
            response.put("status", "success - reservation created");
            response.put("timestamp", String.valueOf(new Date()));

        } catch (DataIntegrityViolationException dive) {
            logger.error("Error occurred while trying to create a new room - login duplicate", dive);
            response.put("timestamp", String.valueOf(new Date()));
            response.put("status", "fail - login duplicate");
        } catch (Exception e) {
            logger.error("Error occurred while trying to create a new room ", e);
            response.put("timestamp", String.valueOf(new Date()));
            response.put("status", "fail - unknown error");
        }

        return response;
    }

    @RequestMapping(value = "/room", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public String bookedRoom(@Valid @RequestBody Reservations reservations) {
        logger.info("Called reservations/room");

        if (!checkRoomNameAndSeats(reservations)) {
            response.put("timestamp", String.valueOf(new Date()));
            return response.toString();
        }

        return reservationsService.getReservationsList(reservations).toString();
    }

    @RequestMapping(value = "/reservationList", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public String bookedRooms(@RequestBody Reservations reservations) {
        logger.info("Called reservations/reservationList");

        return reservationsService.getReservationsList(reservations).toString();
    }

    private boolean checkLoginsAndPasswords(Reservations reservations) {
        for (Employees employees : reservations.getEmployees()) {
            Employees emp = null;
            try {
                emp = employeesService.getEmployeeByLogin(employees.getLogin());
            } catch (Exception e) {
                response.put("status", "fail - login not found: " + employees.getLogin());
                return false;
            }
            logger.info("employee: " + emp);
            assert emp != null;
            if (!emp.getPassword().equals(employees.getPassword())) {
                response.put("status", "fail - incorrect password for login: " + employees.getLogin());
                return false;
            }
        }
        return true;
    }

    private boolean checkRoomNameAndSeats(Reservations reservations) {
        Rooms room;

        try {
            room = roomsService.getRoomByName(reservations.getRooms().getRoomName());
            if (room.getNumberOfSeats() < reservations.getEmployees().size()) {
                response.put("status", "fail - number of participants (" + reservations.getEmployees().size() + ") exceeds number of seats (" + room.getNumberOfSeats() + ")");
                return false;
            }
        } catch (NullPointerException npe){
            response.put("status", "fail - roomName not found");;
            return false;
        }
        return true;
    }

    private boolean checkDates(Reservations reservations) {
        if (reservations.getDateFrom().compareTo(reservations.getDateTo()) >= 0){
            response.put("status", "fail - incorrect date reservation - dateFrom is greater or equals dateTo");
            return false;
        }

        //TODO add checking if room is not booked

        return true;
    }
}
