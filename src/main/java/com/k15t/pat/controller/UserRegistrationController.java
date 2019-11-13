package com.k15t.pat.controller;

import com.k15t.pat.model.User;
import com.k15t.pat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserRegistrationController {
    @Autowired
    private UserService userService;

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
            final List<String> errors = new ArrayList<>();
            for (final FieldError error : bindingResult.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }
            return "error";
        }
        else {
            User newUser = userService.newUser(user);
            System.out.println(newUser);
            model.addAttribute("newUser", newUser);
            model.addAttribute("success", "NO_ERRORS");
            return "registration";
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
            User newUser = userService.newUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }
    }
}
