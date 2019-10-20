package com.roomReservation.service;

import com.roomReservation.model.Employees;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeesService {

    public void createEmployee(Employees employees);

    public void deleteEmployee(String employeeLogin);

    public void updateEmployee(Employees employees);

    public Employees getEmployeeByLogin(String employeeLogin);

    public List<Employees> getEmployees();
}
