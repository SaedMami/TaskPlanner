package org.mami.tasktracker.services;

import org.mami.tasktracker.domain.Project;
import org.mami.tasktracker.exceptions.CustomFieldValidationException;
import org.mami.tasktracker.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project saveOrUpdate(Project projectToSave) {
        try {
            return this.projectRepository.save(projectToSave);
        } catch (DataIntegrityViolationException e) {
            if (projectRepository.findByProjectCode(projectToSave.getProjectCode()).isPresent()) {
                throw new CustomFieldValidationException(
                        "projectCode",
                        String.format("project code %s already exists", projectToSave.getProjectCode()));
            } else {
                throw (e);
            }
        }
    }

    public Project findProjectByCode(String projectCode) {
        return this.projectRepository.findByProjectCode(projectCode)
                .orElseThrow(() -> new CustomFieldValidationException("projectCode",
                        String.format("Could not find a Project with project code: <%s>", projectCode)));
    }

    public Iterable<Project> findAllProjects() {
        return this.projectRepository.findAll();
    }

    public void deleteProjectByCode(String projectCode) {
        Project project = this.findProjectByCode(projectCode);
        this.projectRepository.delete(project);
    }
}
