package com.roomReservation.service;

import com.roomReservation.AbstractTest;
import com.roomReservation.model.Employees;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class EmployeesServiceTest extends AbstractTest {

    @Autowired
    private EmployeesService employeesService;

    @Test
    public void testCreateJsmith(){
        List<Employees> list;
        Employees employees = new Employees("qwer", "qwer", "qwer", "qwerty");
        employeesService.createEmployee(employees);

        list = employeesService.getEmployees();

        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - expected size", 3, list.size());
        for (Employees element:list) {
            logger.info(element.getLogin());
        }
    }

    @Test
    public void testList(){
        List<Employees> list = employeesService.getEmployees();
        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - expected size", 2, list.size());
        for (Employees element:list) {
            logger.info(element.getLogin());
        }
    }

    @Test
    public void testDelete(){
        List<Employees> list;
        String employeeLogin = "jdoe";
        employeesService.deleteEmployee(employeeLogin);

        list = employeesService.getEmployees();

        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - expected size", 1, list.size());
        for (Employees element:list) {
            logger.info(element.getLogin());
        }
    }

    @Test
    public void testDeleteAll(){
        List<Employees> list;
        String employeeLogin = "jdoe";
        employeesService.deleteEmployee(employeeLogin);
        employeeLogin = "jsmith";
        employeesService.deleteEmployee(employeeLogin);

        list = employeesService.getEmployees();

        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - expected size", 0, list.size());
        for (Employees element:list) {
            logger.info(element.getLogin());
        }
        logger.info("list size: " + list.size());
    }

    @Test
    public void testUpdate(){
        List<Employees> list;
        employeesService.updateEmployee(new Employees("qwer", "qwer", "jsmith", "qwerty"));

        list = employeesService.getEmployees();

        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - different firstName", "qwer", list.get(0).getFirstName());
        Assert.assertEquals("failure - different lastName", "qwer", list.get(0).getLastName());
        Assert.assertEquals("failure - different login", "jsmith", list.get(0).getLogin());
        for (Employees element:list) {
            logger.info(element.getLogin());
        }
        logger.info("list size: " + list.size());
    }

    @Test
    public void testUpdateEmptyData(){
        List<Employees> list = employeesService.getEmployees();
        String password = list.get(0).getPassword();
        employeesService.updateEmployee(new Employees("", "", "jsmith", ""));

        list = employeesService.getEmployees();

        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - different firstName", "John", list.get(0).getFirstName());
        Assert.assertEquals("failure - different lastName", "Smith", list.get(0).getLastName());
        Assert.assertEquals("failure - different login", "jsmith", list.get(0).getLogin());
        Assert.assertEquals("failure - different password", password, list.get(0).getPassword());
    }

    @Test
    public void testUpdateNullData(){
        List<Employees> list = employeesService.getEmployees();
        String password = list.get(0).getPassword();
        Employees emp = new Employees();
        emp.setLogin("jsmith");
        employeesService.updateEmployee(emp);

        list = employeesService.getEmployees();

        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - different firstName", "John", list.get(0).getFirstName());
        Assert.assertEquals("failure - different lastName", "Smith", list.get(0).getLastName());
        Assert.assertEquals("failure - different login", "jsmith", list.get(0).getLogin());
        Assert.assertEquals("failure - different password", password, list.get(0).getPassword());
    }
}
