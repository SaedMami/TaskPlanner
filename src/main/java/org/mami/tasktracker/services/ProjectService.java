package org.mami.tasktracker.services;

import org.mami.tasktracker.domain.Project;
import org.mami.tasktracker.exceptions.CustomFieldValidationException;
import org.mami.tasktracker.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        } catch (DataAccessException e) {
            throw new CustomFieldValidationException("projectCode", String.format("project code %s already exists", projectToSave.getProjectCode()));
        }
    }
}
