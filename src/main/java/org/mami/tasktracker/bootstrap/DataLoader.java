package org.mami.tasktracker.bootstrap;

import org.mami.tasktracker.domain.Backlog;
import org.mami.tasktracker.domain.Project;
import org.mami.tasktracker.domain.Task;
import org.mami.tasktracker.services.BacklogService;
import org.mami.tasktracker.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProjectService projectService;
    private final BacklogService backlogService;

    @Autowired
    public DataLoader(ProjectService projectService, BacklogService backlogService) {
        this.projectService = projectService;
        this.backlogService = backlogService;
    }

    @Override
    public void run(String... args) {
        Project project1 = new Project();
        project1.setName("Cool project");
        project1.setDescription("Some cool description");
        project1.setProjectCode("ID01");
        this.projectService.saveOrUpdate(project1);

        Project project2 = new Project();
        project2.setName("Awesome project");
        project2.setDescription("Some awesome description");
        project2.setProjectCode("ID02");
        this.projectService.saveOrUpdate(project2);


        Task task11 = new Task();
        task11.setSummary("Cool task 11");

        Task task12 = new Task();
        task12.setSummary("Cool task 12");

        Task task13 = new Task();
        task13.setSummary("Cool task 13");

        this.backlogService.addTask(project1.getProjectCode(), task11);
        this.backlogService.addTask(project1.getProjectCode(), task12);
        this.backlogService.addTask(project1.getProjectCode(), task13);

        Task task21 = new Task();
        task21.setSummary("Awesome task 21");

        Task task22 = new Task();
        task22.setSummary("Awesome task 22");

        this.backlogService.addTask(project2.getProjectCode(), task21);
        this.backlogService.addTask(project2.getProjectCode(), task22);


        this.projectService.saveOrUpdate(project2);
    }
}
