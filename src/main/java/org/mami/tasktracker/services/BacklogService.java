package org.mami.tasktracker.services;

import org.mami.tasktracker.domain.Backlog;
import org.mami.tasktracker.domain.Task;
import org.mami.tasktracker.exceptions.CustomFieldValidationException;
import org.mami.tasktracker.repositories.BacklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BacklogService {

    private final BacklogRepository backlogRepository;

    @Autowired
    public BacklogService(BacklogRepository backlogRepository) {
        this.backlogRepository = backlogRepository;
    }

    public Task addTask(String projectCode, Task task) {
        Optional<Backlog> backlog = backlogRepository.findByProjectCode(projectCode);

        if (backlog.isPresent()) {
            Task addedTask = backlog.get().addTask(task);
            this.backlogRepository.save(backlog.get());
            return addedTask;
        }
        throw new CustomFieldValidationException("projectNotFound", String.format("Project code: %s does not exist", projectCode));
    }

    public Backlog getProjectBacklog(final String projectCode) {
        return this.backlogRepository.findByProjectCode(projectCode)
                .orElseThrow(() -> new CustomFieldValidationException(
                        "projectCode",
                        String.format("project with code {%s} does not exist", projectCode)));
    }

    public Task getTaskBySequence(final String projectCode, final String sequence) {
        Backlog backlog = this.backlogRepository.findByProjectCode(projectCode)
                .orElseThrow(() -> new CustomFieldValidationException(
                        "projectCode",
                        String.format("project with code {%s} does not exist", projectCode)));

        return backlog.getTasks().stream()
                .filter(task -> task.getProjectSequence().equals(sequence))
                .findAny()
                .orElseThrow(() -> new CustomFieldValidationException(
                        "taskSequence",
                        String.format("could not find task <%s> within the project", sequence)));
    }
}
