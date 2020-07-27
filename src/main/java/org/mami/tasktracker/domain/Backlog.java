package org.mami.tasktracker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.mami.tasktracker.exceptions.CustomFieldValidationException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Backlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer PTSequence = 0;

    private String projectCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, unique = true)
    @JsonIgnore
    private Project project;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "backlog_id")
    private List<Task> tasks = new ArrayList<>();

    public Backlog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPTSequence() {
        return PTSequence;
    }

    public void setPTSequence(Integer PTSequence) {
        this.PTSequence = PTSequence;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task addTask(Task task) {
        task.setProjectSequence(String.format("%s_%d", this.projectCode, this.PTSequence++));
        task.setProjectCode(this.projectCode);

        if (task.getPriority() == null || task.getPriority() == 0) {
            task.setPriority(3);
        }

        if (task.getStatus() == null || task.getStatus().isEmpty()) {
            task.setStatus("TO_DO");
        }

        this.tasks.add(task);
        return task;
    }

    public Task updateTask(Task task) {
        // find the task
        Task toUpdate = this.tasks.stream()
                .filter(t -> t.getProjectSequence().equals(task.getProjectSequence())).findFirst()
                .orElseThrow( () -> new CustomFieldValidationException("taskSequence",
                        String.format("Could not find a task with sequence {%s} in project {%s}",
                                task.getProjectSequence(),
                                this.getProjectCode())));

        toUpdate.setSummary(task.getSummary());
        toUpdate.setPriority(task.getPriority());
        toUpdate.setDueDate(task.getDueDate());
        toUpdate.setAcceptanceCriteria(task.getAcceptanceCriteria());
        toUpdate.setStatus(task.getStatus());

        return toUpdate;
    }

    public void removeTask(String taskSequence) {
        this.tasks.removeIf(task -> task.getProjectSequence().equals(taskSequence));
    }
}
