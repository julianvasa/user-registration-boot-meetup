package com.k15t.pat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

/**
 * User entity to persist data to the H2 database
 * Field validation are defined in this class
 *
 * @author Julian Vasa
 */
@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Please fill in the name")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Name should contains only letters, no numbers or special characters!")
    @Length(min = 2)
    private String name;

    @NotEmpty(message = "Please fill in a password")
    @Length(min = 8, message = "Password should be min 8 characters!")
    private String password;

    @NotEmpty(message = "Please fill in the email")
    @Pattern(regexp = ".+@.+\\..+", message = "Wrong email!")
    private String email;

    @NotEmpty(message = "Please fill in the address")
    private String address;

    private String city;

    private String country;

    @Pattern(regexp = "^[0-9+]*$", message = "Phone should contains only numbers and/or +!")
    private String phone;
}
