package org.mami.tasktracker.services;

import org.mami.tasktracker.domain.Backlog;
import org.mami.tasktracker.domain.Project;
import org.mami.tasktracker.domain.User;
import org.mami.tasktracker.exceptions.CustomFieldValidationException;
import org.mami.tasktracker.repositories.ProjectRepository;
import org.mami.tasktracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Project saveOrUpdate(Project projectToSave, String username) {
            // find the user, this is guaranteed to exist since you cannot get here without a valid token
            User user = this.userRepository.findByUsername(username);

            if (projectToSave.getId() == null) {
                // new project
                if (projectRepository.findByProjectCode(projectToSave.getProjectCode()).isPresent()) {
                    throw new CustomFieldValidationException(
                            "projectCode",
                            String.format("project code %s already exists", projectToSave.getProjectCode()));
                }

                Backlog backlog = new Backlog();
                projectToSave.setBacklog(backlog);
                projectToSave.setUser(user);
                return this.projectRepository.save(projectToSave);

            } else {
                // update the project
                Project toBeUpdated = this.findProjectByCode(projectToSave.getProjectCode(), username);

                toBeUpdated.setName(projectToSave.getName());
                toBeUpdated.setDescription(projectToSave.getDescription());
                toBeUpdated.setStartDate(projectToSave.getStartDate());
                toBeUpdated.setEndDate(projectToSave.getEndDate());

                return this.projectRepository.save(toBeUpdated);
            }
    }

    public Project findProjectByCode(String projectCode, String username) {

        Project project = this.projectRepository.findByProjectCode(projectCode)
                .orElseThrow(() -> new CustomFieldValidationException("projectCode",
                        String.format("Could not find a Project with project code: <%s>", projectCode)));

        if (!project.getUser().getUsername().equals(username)) {
            throw new CustomFieldValidationException("username", "user does not have permission to view project");
        }
        return  project;
    }

    public Iterable<Project> findAllProjects(String username) {
        return this.projectRepository.findAllByUser_Username(username);
    }

    public void deleteProjectByCode(String projectCode, String username) {
        Project project = this.findProjectByCode(projectCode, username);
        this.projectRepository.delete(project);
    }
}
