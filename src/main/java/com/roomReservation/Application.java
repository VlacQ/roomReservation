package com.roomReservation;

import com.roomReservation.enumerate.Projector;
import com.roomReservation.model.Employees;
import com.roomReservation.model.Rooms;
import com.roomReservation.service.EmployeesService;
import com.roomReservation.service.RoomsService;
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

    @Autowired
    private RoomsService roomsService;

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    InitializingBean initialData(){
        return () -> {
            employeesService.createEmployee(new Employees("John", "Smith", "jsmith", "qwerty"));
            employeesService.createEmployee(new Employees("Jane", "Doe", "jdoe", "mySecret"));
            roomsService.createRoom(new Rooms("Large Room", "1st floor", 10, Projector.YES, "22-22-22-22"));
            roomsService.createRoom(new Rooms("Medium Room", "1st floor", 6, Projector.YES, ""));
            roomsService.createRoom(new Rooms("Small Room", "2nd floor", 4, Projector.NO, ""));
        };
    }
}
