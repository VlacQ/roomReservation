package com.roomReservation.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Employees")
public class Employees {
    @Column(name = "first_name", nullable = false)
    @Length(max = 50)
    @NotEmpty(message = "first name cannot be empty")
    @NotNull(message = "first name cannot be empty")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Length(max = 100, message = "last name ")
    @NotEmpty(message = "last name cannot be empty")
    @NotNull(message = "last name cannot be empty")
    private String lastName;

    @Id
    @Column(unique = true)
    @Length(max = 100)
    @NotEmpty(message = "login cannot be empty")
    @NotNull(message = "login cannot be empty")
    private String login;

    @Column(nullable = false)
    @Length(max = 100, min = 6, message = "password cannot have more than 100 characters")
    @Length(min = 6, message = "password cannot have less than 6 characters")
    private String password;

    public Employees() {
    }

    public Employees(@Length(max = 50) String firstName, @Length(max = 100) String lastName, @Length(max = 100) @NotEmpty String login, @Length(max = 100, min = 6) String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = securePassword(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = securePassword(password);
    }

    public String securePassword(String password) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;
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
