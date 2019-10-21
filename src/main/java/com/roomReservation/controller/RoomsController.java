package com.roomReservation.controller;

import com.roomReservation.model.Rooms;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
@EnableAutoConfiguration
@RequestMapping("/rooms")
public class RoomsController {
    @Autowired
    private RoomsService roomsService;

    private Map<String, String> response = new HashMap<String, String>();

    private static final Logger logger = LoggerFactory.getLogger(RoomsController.class);

    @RequestMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Map<String, String> createRoom(@Valid @RequestBody Rooms rooms, @RequestParam("passwd") String password) {
        logger.info("Called rooms/create");

        if (checkPassword(password)){
            logger.info("Correct password");
        } else {
            logger.error("Error occurred while trying to create a new room - incorrect password");
            response.put("timestamp", String.valueOf(new Date()));
            response.put("status", "fail - incorrect password");
            return response;
        }

        try {
            roomsService.createRoom(rooms);
            response.put("status", "success - room created");

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

    @RequestMapping(value = "/delete/{roomName}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Map<String, String> deleteEmployee(@PathVariable String roomName, @RequestParam("passwd") String password) {
        logger.info("Called rooms/delete");

        if (checkPassword(password)){
            logger.info("Correct password");
        } else {
            logger.error("Error occurred while trying to delete a room - incorrect password");
            response.put("timestamp", String.valueOf(new Date()));
            response.put("status", "fail - incorrect password");
            return response;
        }

        try {
            roomsService.deleteRoom(roomName);
            response.put("timestamp", String.valueOf(new Date()));
            response.put("status", "success");
        } catch (Exception e) {
            logger.error("Error occurred while trying to delete a room ", e);
            response.put("timestamp", String.valueOf(new Date()));
            response.put("status", "fail");
        }

        return response;
    }

    @RequestMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Map<String, String> updateEmployee(@RequestBody Rooms rooms, @RequestParam("passwd") String password) {
        logger.info("Called rooms/update");

        if (checkPassword(password)){
            logger.info("Correct password");
        } else {
            logger.error("Error occurred while trying to update a room - incorrect password");
            response.put("timestamp", String.valueOf(new Date()));
            response.put("status", "fail - incorrect password");
            return response;
        }

        try {
            Rooms r =  roomsService.getRoomByName(rooms.getRoomName());
            if (r.getRoomName() == null){
                response.put("status", "fail - login not found");
                response.put("timestamp", String.valueOf(new Date()));
                return response;
            }
            roomsService.updateRoom(rooms);
            response.put("timestamp", String.valueOf(new Date()));
            response.put("status", "success");

        } catch (Exception e) {
            logger.error("Error occurred while trying to update a room ", e);
            response.put("timestamp", String.valueOf(new Date()));
            response.put("status", "fail - unknown error");
        }

        return response;
    }

    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public String getRoomsList() {
        logger.info("Called rooms/list");
        return roomsService.getRooms().toString();
    }

    private static boolean checkPassword(String password){
        try {
            Properties prop = new Properties();
            prop.load(RoomsController.class.getResourceAsStream("/application.properties"));
            return prop.getProperty("application.password").equals(password);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
