package org.mami.tasktracker.services;

import org.mami.tasktracker.domain.Backlog;
import org.mami.tasktracker.domain.Task;
import org.mami.tasktracker.repositories.BacklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BacklogService {

    private BacklogRepository backlogRepository;

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

        return null;
    }

    public Backlog getProjectBacklog(String projectCode) {
        return this.backlogRepository.findByProjectCode(projectCode).get();
    }
}
