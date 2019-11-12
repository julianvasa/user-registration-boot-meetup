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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * User entity to persist data to the H2 database
 *
 * @author Julian Vasa
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Please fill in the name")
    @NotBlank(message = "Please fill in the name")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Name should contains only letters, no numbers or special characters!")
    @Length(min = 2)
    private String name;

    @NotNull(message = "Please fill in a password")
    @NotBlank(message = "Please fill in a password")
    @Length(min = 8, message = "Password should be min 8 characters!")
    private String password;

    @NotNull(message = "Please fill in the email")
    @NotBlank(message = "Please fill in the email")
    @Pattern(regexp = ".+@.+\\..+", message = "Wrong email!")
    private String email;

    @NotNull(message = "Please fill in the address")
    @NotBlank(message = "Please fill in the address")
    private String address;

    @Pattern(regexp = "\\(?\\+\\(?49\\)?[ ()]?([- ()]?\\d[- ()]?){10}", message = "Phone number not in the correct format! Ex: +491739341284")
    private String phone;
}
