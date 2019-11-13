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

    /**
     * /
     *
     * @return redirects to registration.html
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/registration.html";
    }

    /**
     * GET /registration.html returns the name of the template to render
     * (Thymeleaf adds .html extension and searches in the resources/templates folder)
     *
     * @param model
     * @return name of the template
     */
    @GetMapping("/registration.html")
    public String registration(Model model) {
        return "registration";
    }

    /**
     * An POST endpoint to create a new user from the frontend form
     * @param user User data is bind from the fields of the form in the registration template to a object of type User
     * @param bindingResult bean validation errors
     * @return error template if bindingResult.hasErrors()
     * or else returns the registration template with the new user that was created in the DB with a proper id and encrypted password
     */
    @PostMapping("/rest/registration")
    public String newRegistration(Model model, @Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        else {
            try {
                User newUser = userService.newUser(user);
                // Flags needed in the template to not show the input form once form has been submitted and the user has been created
                model.addAttribute("newUser", newUser);
                model.addAttribute("success", true);
                // Name of the template to render with the above attributes
                return "registration";
            } catch (UserAlreadyExistsException e) {
                model.addAttribute("already_exists", true);
                // In case the name already exists in the DB return name of the template
                // to render with the above attribute to show the proper message to the client
                return "error";
            }
        }
    }

    /**
     * An POST endpoint to create a new user from rest api request
     * @param user User data is send to the server in the request body
     * @param bindingResult bean validation errors
     * @return errors if bindingResult.hasErrors()
     * or else returns the new user that was created in the DB with a proper id and encrypted password
     */
    @PostMapping("/api/registration")
    public ResponseEntity newRegistrationApi(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            final List<String> errors = new ArrayList<>();
            for (final FieldError error : bindingResult.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }
            // Create a new ResponseEntity with a list of errors
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        else {
            try {
                User newUser = userService.newUser(user);
                // Return a ResponseEntity with newUser as body
                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
            } catch (UserAlreadyExistsException e) {
                UserAlreadyExists userAlreadyExistsError = UserAlreadyExists.builder().code(USER_ALREADY_EXISTS).description(A_USER_WITH_THE_GIVEN_USERNAME_ALREADY_EXISTS).build();
                // In case the name already exists in the DB, return a ResponseEntity with userAlreadyExistsError as body
                return new ResponseEntity<>(userAlreadyExistsError, HttpStatus.CONFLICT);
            }
        }
    }

    /**
     * GET /rest/registration redirects to home
     *
     * @return redirect to home template
     */
    @GetMapping(value = "/rest/registration")
    public String redirectTohHome() {
        return REDIRECT_TO_HOME;
    }

}
