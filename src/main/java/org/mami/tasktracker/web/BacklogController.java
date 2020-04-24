package org.mami.tasktracker.web;

import org.mami.tasktracker.domain.Task;
import org.mami.tasktracker.services.BacklogService;
import org.mami.tasktracker.services.ValidationReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    private BacklogService backlogService;
    private ValidationReportingService validationReportingService;

    @Autowired
    public BacklogController(BacklogService taskService, ValidationReportingService validationReportingService) {
        this.backlogService = taskService;
        this.validationReportingService = validationReportingService;
    }

    @PostMapping("/{projectCode}")
    public ResponseEntity<?> addTaskToBacklog(@Valid @RequestBody Task task, @PathVariable String projectCode, BindingResult result) {
        if (result.hasErrors()) {
            return validationReportingService.reportValidationErrors(result);
        }

        Task createdTask = this.backlogService.addTask(projectCode, task);

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }
}
