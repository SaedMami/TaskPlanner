package org.mami.tasktracker.services;

import org.mami.tasktracker.domain.Project;
import org.mami.tasktracker.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project saveOrUpdate(Project projectToSave) {
        return this.projectRepository.save(projectToSave);
    }
}
