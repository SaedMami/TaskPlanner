package org.mami.tasktracker.web;

import org.mami.tasktracker.services.UserService;
import org.mami.tasktracker.services.ValidationReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.mami.tasktracker.domain.User;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private ValidationReportingService validationReportingService;
    private UserService userService;

    @Autowired
    public UserController(ValidationReportingService validationReportingService, UserService userService) {
        this.validationReportingService = validationReportingService;
        this.userService = userService;
    }

    @PostMapping("/register")
   public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
       if (result.hasErrors()) {
           return this.validationReportingService.reportValidationErrors(result);
       }

       User newUser = userService.registerUser(user);

       return new ResponseEntity<>(newUser, HttpStatus.CREATED);
   }


}
