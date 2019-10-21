package com.roomReservation.controller;

import com.roomReservation.model.Employees;
import com.roomReservation.service.EmployeesService;
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
@RequestMapping("/employees")
public class EmployeesController {
    @Autowired
    private EmployeesService employeesService;

    private Map<String, String> response = new HashMap<String, String>();

    private static final Logger logger = LoggerFactory.getLogger(EmployeesController.class);

    @RequestMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Map<String, String> createEmployee(@Valid @RequestBody Employees employees, @RequestParam("passwd") String password) {
        logger.info("Called employees/create");

        if (checkPassword(password)){
            logger.info("Correct password");
        } else {
            logger.error("Error occurred while trying to create a new employee - incorrect password");
            response.put("timestamp", String.valueOf(new Date()));
            response.put("status", "fail - incorrect password");
            return response;
        }

        try {
            employeesService.createEmployee(employees);
            employees.setPassword(employees.securePassword(employees.getPassword()));
            response.put("timestamp", String.valueOf(new Date()));
            response.put("status", "success - employee created");

        } catch (DataIntegrityViolationException dive) {
            logger.error("Error occurred while trying to create a new employee - login duplicate", dive);
            response.put("timestamp", String.valueOf(new Date()));
            response.put("status", "fail - login duplicate");
        } catch (Exception e) {
            logger.error("Error occurred while trying to create a new employee ", e);
            response.put("timestamp", String.valueOf(new Date()));
            response.put("status", "fail - unknown error");
        }

        return response;
    }

    @RequestMapping(value = "/delete/{employeeLogin}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Map<String, String> deleteEmployee(@PathVariable String employeeLogin, @RequestParam("passwd") String password) {
        logger.info("Called employees/delete");

        if (checkPassword(password)){
            logger.info("Correct password");
        } else {
            logger.error("Error occurred while trying to create a new employee - incorrect password");
            response.put("status", "fail - incorrect password");
            response.put("timestamp", String.valueOf(new Date()));
            return response;
        }

        try {
            employeesService.deleteEmployee(employeeLogin);
            response.put("status", "success");
            response.put("timestamp", String.valueOf(new Date()));
        } catch (Exception e) {
            logger.error("Error occurred while trying to delete an employee ", e);
            response.put("status", "fail");
            response.put("timestamp", String.valueOf(new Date()));
        }

        return response;
    }

    @RequestMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Map<String, String> updateEmployee(@RequestBody Employees employees, @RequestParam("passwd") String password) {
        logger.info("Called employees/update");

        if (checkPassword(password)){
            logger.info("Correct password");
        } else {
            logger.error("Error occurred while trying to create a new employee - incorrect password");
            response.put("status", "fail - incorrect password");
            response.put("timestamp", String.valueOf(new Date()));
            return response;
        }

        try {
            Employees emp =  employeesService.getEmployeeByLogin(employees.getLogin());
            if (emp.getLogin() == null){
                response.put("status", "fail - login not found");
                response.put("timestamp", String.valueOf(new Date()));
                return response;
            }
            employeesService.updateEmployee(employees);

        } catch (Exception e) {
            logger.error("Error occurred while trying to delete an employee ", e);
            response.put("timestamp", String.valueOf(new Date()));
            response.put("status", "fail");
        }

        return response;
    }

    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public String getEmployeesList() {
        logger.info("employees/list");
        return employeesService.getEmployees().toString();
    }

    private static boolean checkPassword(String password){
        try {
            Properties prop = new Properties();
            prop.load(EmployeesController.class.getResourceAsStream("/application.properties"));
            return prop.getProperty("application.password").equals(password);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
