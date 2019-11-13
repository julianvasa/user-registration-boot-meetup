package com.k15t.pat.controller;

import com.k15t.pat.exception.UserAlreadyExistsException;
import com.k15t.pat.model.User;
import com.k15t.pat.model.UserAlreadyExists;
import com.k15t.pat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Main controller to handle requests for user registration
 *
 * @author Julian Vasa
 */
@Controller
public class UserRegistrationController {
    @Autowired
    private UserService userService;

    private static final String REDIRECT_TO_HOME = "redirect:/registration.html";

    @Value("${username_already_exists.code}")
    private String USER_ALREADY_EXISTS;
    @Value("${username_already_exists.description}")
    private String A_USER_WITH_THE_GIVEN_USERNAME_ALREADY_EXISTS;


    @GetMapping("/")
    public String index() {
        return "redirect:/registration.html";
    }

    @GetMapping("/registration.html")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/rest/registration")
    public String newRegistration(Model model, @Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        else {
            try {
                User newUser = userService.newUser(user);
                model.addAttribute("newUser", newUser);
                model.addAttribute("success", true);
                return "registration";
            } catch (UserAlreadyExistsException e) {
                model.addAttribute("already_exists", true);
                return "error";
            }
        }
    }

    @PostMapping("/api/registration")
    public ResponseEntity newRegistrationApi(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            final List<String> errors = new ArrayList<>();
            for (final FieldError error : bindingResult.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        else {
            try {
                User newUser = userService.newUser(user);
                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
            } catch (UserAlreadyExistsException e) {
                UserAlreadyExists userAlreadyExistsError = UserAlreadyExists.builder().code(USER_ALREADY_EXISTS).description(A_USER_WITH_THE_GIVEN_USERNAME_ALREADY_EXISTS).build();
                return new ResponseEntity<>(userAlreadyExistsError, HttpStatus.CONFLICT);
            }
        }
    }


    @GetMapping(value = "/rest/registration")
    public String redirectTohHome() {
        return REDIRECT_TO_HOME
            ;
    }

}
