package org.mami.tasktracker.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ValidationReportingService {
    public ResponseEntity<Map<String, String>> reportValidationErrors(BindingResult result) {

            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
