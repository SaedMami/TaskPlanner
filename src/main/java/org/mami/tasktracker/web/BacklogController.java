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
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    private final BacklogService backlogService;
    private final ValidationReportingService validationReportingService;

    @Autowired
    public BacklogController(BacklogService taskService, ValidationReportingService validationReportingService) {
        this.backlogService = taskService;
        this.validationReportingService = validationReportingService;
    }

    @PostMapping("/{projectCode}")
    public ResponseEntity<?> addTaskToBacklog(
            @Valid @RequestBody Task task,
            BindingResult result,
            @PathVariable String projectCode,
            Principal principal) {
        if (result.hasErrors()) {
            return validationReportingService.reportValidationErrors(result);
        }

        Task createdTask = this.backlogService.addTask(projectCode, task, principal.getName());

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PatchMapping("/{projectCode}/{taskSequence}")
    public ResponseEntity<?>  updateTask(
            @Valid @RequestBody Task task,
            BindingResult result,
            @PathVariable String projectCode,
            @PathVariable String taskSequence,
            Principal principal) {

        if (result.hasErrors()) {
            return validationReportingService.reportValidationErrors(result);
        }

        return new ResponseEntity<>(this.backlogService.updateTask(projectCode, task, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{projectCode}")
    public ResponseEntity<?> getProjectBacklog(@PathVariable String projectCode, Principal principal) {
        return new ResponseEntity<>(this.backlogService.getProjectBacklog(projectCode, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{projectCode}/{taskSequence}")
    public ResponseEntity<?> getProjectTask(
            @PathVariable String projectCode,
            @PathVariable String taskSequence,
            Principal principal) {
        return new ResponseEntity<>(this.backlogService.getTaskBySequence(projectCode, taskSequence, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/{projectCode}/{taskSequence}")
    public ResponseEntity<?> deleteProjectByCode(
            @PathVariable String projectCode,
            @PathVariable String taskSequence,
            Principal principal) {
        this.backlogService.deleteTask(projectCode, taskSequence, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
