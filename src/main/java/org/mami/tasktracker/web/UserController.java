package org.mami.tasktracker.web;

import org.mami.tasktracker.payload.JWTLoginSuccessResponse;
import org.mami.tasktracker.payload.LoginRequest;
import org.mami.tasktracker.security.JwtTokenProvider;
import org.mami.tasktracker.security.SecurityConstants;
import org.mami.tasktracker.services.UserService;
import org.mami.tasktracker.services.ValidationReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final ValidationReportingService validationReportingService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(ValidationReportingService validationReportingService,
                          UserService userService,
                          JwtTokenProvider jwtTokenProvider,
                          AuthenticationManager authenticationManager){
        this.validationReportingService = validationReportingService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        if (result.hasErrors()) {
            return this.validationReportingService.reportValidationErrors(result);
        }

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
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
