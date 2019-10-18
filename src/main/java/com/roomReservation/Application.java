package com.roomReservation;

import com.roomReservation.model.Employees;
import com.roomReservation.service.EmployeesService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Application {

    @Autowired
    private EmployeesService employeesService;

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    InitializingBean initialData(){
        return () -> {
            employeesService.createEmployee(new Employees("John", "Smith", "jsmith", "qwerty"));
            employeesService.createEmployee(new Employees("Jane", "Doe", "jdoe", "mySecret"));
        };
    }
}
