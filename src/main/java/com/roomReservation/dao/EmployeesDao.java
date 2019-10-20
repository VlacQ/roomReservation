package com.roomReservation.dao;

import com.roomReservation.model.Employees;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Repository
public class EmployeesDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void createEmployee(Employees employees){
        entityManager.persist(employees);
    }

    public void updateEmployee(Employees employees){
        entityManager.merge(employees);
    }

    public Employees getEmployeeByLogin(String login){
        return entityManager.find(Employees.class, login);
    }

    public Collection<Employees> getEmployees(){
        Query query = entityManager.createQuery("SELECT e FROM Employees e");
        return (Collection<Employees>) query.getResultList();
    }

    public void deleteEmployee(String login){
        Employees employees = getEmployeeByLogin(login);
        if (employees != null)
            entityManager.remove(employees);
    }
}
