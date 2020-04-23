package org.mami.tasktracker.web;

import org.mami.tasktracker.domain.Project;
import org.mami.tasktracker.services.ProjectService;
import org.mami.tasktracker.services.ValidationReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    private final ProjectService projectService;
    private final ValidationReportingService validationReportingService;

    @Autowired
    public ProjectController(ProjectService projectService, ValidationReportingService validationReportingService) {
        this.projectService = projectService;
        this.validationReportingService = validationReportingService;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@RequestBody @Valid Project project, BindingResult result) {
        if (result.hasErrors()) {
            return validationReportingService.reportValidationErrors(result);
        }

        Project createdProject = this.projectService.saveOrUpdate(project);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @GetMapping("/{projectCode}")
    public ResponseEntity<?> getProjectByCode(@PathVariable String projectCode) {
        Project foundProject = this.projectService.findProjectByCode(projectCode);

        return new ResponseEntity<>(foundProject, HttpStatus.OK);
    }

    @GetMapping("")
    public Iterable<Project> getAllProjects() {
        return this.projectService.findAllProjects();
    }

    @DeleteMapping("/{projectCode}")
    public ResponseEntity<?> deleteProjectByCode(@PathVariable String projectCode) {
        this.projectService.deleteProjectByCode(projectCode);

        return new ResponseEntity<>(String.format("Project with code: %s was deleted", projectCode), HttpStatus.OK);
    }

}
