package com.roomReservation.service;

import com.roomReservation.AbstractTest;
import com.roomReservation.model.Employees;
import com.roomReservation.model.Reservations;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
public class ReservationsServiceTest extends AbstractTest {

    @Autowired
    private ReservationsService reservationsService;

    @Autowired
    private EmployeesService employeesService;

    @Test
    public void testListNull(){
        Reservations res = new Reservations();
        List<Reservations> list;

        list = reservationsService.getReservationsList(res);
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void testCreate(){
        Reservations res = new Reservations();
        List<Reservations> reservationsList;
        List<Employees> employeesList = employeesService.getEmployees();
        Set<Employees> empSet = new HashSet<>();
        empSet.add(employeesList.get(0));
        empSet.add(employeesList.get(1));

        res.setDateFrom("2019-08-05 12:00:00");
        res.setDateTo("2019-08-05 12:30:00");

        res.setEmployees(empSet);

        reservationsService.createReservation(res);

        res = new Reservations();

        reservationsList = reservationsService.getReservationsList(res);

        Assert.assertNotNull("failure - expected not null", reservationsList);
        Assert.assertEquals("failure - expected 1", 1, reservationsList.get(0).getId());
        Assert.assertEquals("failure - expected 2019-08-05 12:00:00", "2019-08-05 12:00:00", reservationsList.get(0).getDateFrom());
        Assert.assertEquals("failure - expected 2019-08-05 12:30:00", "2019-08-05 12:30:00", reservationsList.get(0).getDateTo());
    }
}
