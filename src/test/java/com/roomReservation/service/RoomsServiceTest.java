package com.roomReservation.service;

import com.roomReservation.AbstractTest;
import com.roomReservation.enumerate.Projector;
import com.roomReservation.model.Employees;
import com.roomReservation.model.Rooms;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class RoomsServiceTest extends AbstractTest {

    @Autowired
    private RoomsService roomsService;

    @Test
    public void testCreate(){
        List<Rooms> list;
        Rooms rooms = new Rooms("qwer", "qwer", 4, Projector.YES, "123-4325-34");
        roomsService.createRoom(rooms);

        list = roomsService.getRooms();

        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - expected size", 4, list.size());
        Assert.assertEquals("failure - different roomName", "qwer", list.get(3).getRoomName());
        Assert.assertEquals("failure - different locationDescription", "qwer", list.get(3).getLocationDescription());
        Assert.assertEquals("failure - different numberOfSeats", 4, list.get(3).getNumberOfSeats());
        Assert.assertEquals("failure - different projector", Projector.YES, list.get(3).getProjector());
        Assert.assertEquals("failure - different phoneNumber", "123-4325-34", list.get(3).getPhoneNumber());
        for (Rooms element:list) {
            logger.info(element.getRoomName());
        }
    }

    @Test
    public void testList(){
        List<Rooms> list = roomsService.getRooms();
        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - expected size", 3, list.size());
        for (Rooms element:list) {
            logger.info(element.getRoomName());
        }
    }

    @Test
    public void testDelete(){
        List<Rooms> list;
        String roomName = "Large Room";
        roomsService.deleteRoom(roomName);

        list = roomsService.getRooms();

        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - expected size", 2, list.size());
        for (Rooms element:list) {
            logger.info(element.getRoomName());
        }
    }

    @Test
    public void testDeleteAll(){
        List<Rooms> list;
        String roomName = "Large Room";
        roomsService.deleteRoom(roomName);
        roomName = "Medium Room";
        roomsService.deleteRoom(roomName);
        roomName = "Small Room";
        roomsService.deleteRoom(roomName);

        list = roomsService.getRooms();

        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - expected size", 0, list.size());
        for (Rooms element:list) {
            logger.info(element.getRoomName());
        }
        logger.info("list size: " + list.size());
    }

    @Test
    public void testUpdate(){
        List<Rooms> list;
        roomsService.updateRoom(new Rooms("Large Room", "3rd floor", 12, Projector.NO, "321-654-897"));

        list = roomsService.getRooms();

        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - different roomName", "Large Room", list.get(0).getRoomName());
        Assert.assertEquals("failure - different locationDescription", "3rd floor", list.get(0).getLocationDescription());
        Assert.assertEquals("failure - different numberOfSeats", 12, list.get(0).getNumberOfSeats());
        Assert.assertEquals("failure - different projector", Projector.NO, list.get(0).getProjector());
        Assert.assertEquals("failure - different phoneNumber", "321-654-897", list.get(0).getPhoneNumber());
        for (Rooms element:list) {
            logger.info(element.getRoomName());
        }
        logger.info("list size: " + list.size());
    }
}
