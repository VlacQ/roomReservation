package com.roomReservation.service.impl;

import com.roomReservation.dao.EmployeesDao;
import com.roomReservation.model.Employees;
import com.roomReservation.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class EmployeesServiceImpl implements EmployeesService {
    private EmployeesDao employeesDao;

    @Autowired
    public void setEmployeesDao(EmployeesDao employeesDao) {
        this.employeesDao = employeesDao;
    }

    @Transactional
    public void createEmployee(Employees employees) {
        employeesDao.createEmployee(employees);
    }

    @Transactional
    public void deleteEmployee(String employeeLogin) {
        employeesDao.deleteEmployee(employeeLogin);
    }

    @Transactional
    public Employees getEmployeeByLogin(String employeeLogin) {
        return employeesDao.getEmployeeByLogin(employeeLogin);
    }

    @Transactional
    public void updateEmployee(Employees employees) {
        Employees emp = getEmployeeByLogin(employees.getLogin());
        if (employees.getFirstName() != null && !employees.getFirstName().equals("")){
            emp.setFirstName(employees.getFirstName());
        }
        if (employees.getLastName() != null && !employees.getLastName().equals("")){
            emp.setLastName(employees.getLastName());
        }
        if (employees.getPassword() != null && !employees.getPassword().equals(emp.securePassword(""))){
            emp.setPassword(employees.getPassword());
        }
    }

    @Transactional
    public List<Employees> getEmployees() {
        return (List<Employees>) employeesDao.getEmployees();
    }
}
