package com.roomReservation.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.roomReservation.enumerate.Projector;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Rooms")
public class Rooms {
    @Id
    @Column(name = "room_name", unique = true, nullable = false)
    @Length(max = 50)
    private String roomName;

    @Column(name = "location_description")
    @Length(max = 256)
    private String locationDescription;

    @Column(name = "number_of_seats", nullable = false)
    private Integer numberOfSeats;

    @Column
    @Enumerated(EnumType.STRING)
    private Projector projector;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_name")
    private List<Reservations> reservationsList = new ArrayList<>();

    public Rooms() {
    }

    public Rooms(@Length(max = 50) String roomName, @Length(max = 256) String locationDescription, @Length(max = 100) @NotEmpty int numberOfSeats, Projector projector, String phoneNumber) {
        this.roomName = roomName;
        this.locationDescription = locationDescription;
        this.numberOfSeats = numberOfSeats;
        this.projector = projector;
        this.phoneNumber = phoneNumber;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Projector getProjector() {
        return projector;
    }

    public void setProjector(Projector projector) {
        this.projector = projector;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        String jsonString = "";
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            jsonString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }
}
