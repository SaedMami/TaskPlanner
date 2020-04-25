package org.mami.tasktracker.services;

import org.mami.tasktracker.domain.Backlog;
import org.mami.tasktracker.domain.Project;
import org.mami.tasktracker.exceptions.CustomFieldValidationException;
import org.mami.tasktracker.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project saveOrUpdate(Project projectToSave) {
            if (projectToSave.getId() == null) {
                // new project
                if (projectRepository.findByProjectCode(projectToSave.getProjectCode()).isPresent()) {
                    throw new CustomFieldValidationException(
                            "projectCode",
                            String.format("project code %s already exists", projectToSave.getProjectCode()));
                }

                Backlog backlog = new Backlog();
                projectToSave.setBacklog(backlog);

                return this.projectRepository.save(projectToSave);

            } else {
                // update the project
                Project toBeUpdated = this.projectRepository.findByProjectCode(projectToSave.getProjectCode()).get();
                toBeUpdated.setName(projectToSave.getName());
                toBeUpdated.setDescription(projectToSave.getDescription());
                toBeUpdated.setStartDate(projectToSave.getStartDate());
                toBeUpdated.setEndDate(projectToSave.getEndDate());

                return this.projectRepository.save(toBeUpdated);
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
