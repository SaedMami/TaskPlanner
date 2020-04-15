package org.mami.tasktracker.web;

import org.mami.tasktracker.domain.Project;
import org.mami.tasktracker.services.ProjectService;
import org.mami.tasktracker.services.ValidationReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;
    private final ValidationReportingService validationReportingService;

    @Autowired
    public ProjectController(ProjectService projectService, ValidationReportingService validationReportingService) {
        this.projectService = projectService;
        this.validationReportingService = validationReportingService;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {

        if (result.hasErrors()) {
            return validationReportingService.reportValidationErrors(result);
        }

        Project createdProject = this.projectService.saveOrUpdate(project);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }


}
