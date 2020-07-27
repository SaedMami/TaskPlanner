package org.mami.tasktracker.services;

import org.mami.tasktracker.domain.Backlog;
import org.mami.tasktracker.domain.Project;
import org.mami.tasktracker.domain.Task;
import org.mami.tasktracker.exceptions.CustomFieldValidationException;
import org.mami.tasktracker.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BacklogService {

    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    @Autowired
    public BacklogService(ProjectRepository projectRepository, ProjectService projectService) {
        this.projectRepository = projectRepository;
        this.projectService = projectService;
    }

    public Task addTask(String projectCode, Task task, String username) {
        Project project = this.projectService.findProjectByCode(projectCode, username);
        Task addedTask = project.getBacklog().addTask(task);
        this.projectRepository.save(project);
        return addedTask;
    }

    public Backlog getProjectBacklog(final String projectCode, String username) {
        Project project = this.projectService.findProjectByCode(projectCode, username);
        return project.getBacklog();
    }

    public Task getTaskBySequence(final String projectCode, final String sequence, String username) {
        Project project = this.projectService.findProjectByCode(projectCode, username);
        Backlog backlog = project.getBacklog();
        return backlog.getTasks().stream()
                .filter(task -> task.getProjectSequence().equals(sequence))
                .findAny()
                .orElseThrow(() -> new CustomFieldValidationException(
                        "taskSequence",
                        String.format("could not find task <%s> within the project", sequence)));
    }

    public Task updateTask(final String projectCode, final Task newTask, String username) {
        Project project = this.projectService.findProjectByCode(projectCode, username);
        Backlog backlog = project.getBacklog();

        Task updatedTask = backlog.updateTask(newTask);
        this.projectRepository.save(project);

        return updatedTask;
    }

    public void deleteTask(final String projectCode, final String taskSequence, String username) {
        Project project = this.projectService.findProjectByCode(projectCode, username);
        Backlog backlog = project.getBacklog();
        backlog.removeTask(taskSequence);
        this.projectRepository.save(project);
    }
}
