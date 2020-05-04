package org.mami.tasktracker.bootstrap;

import org.mami.tasktracker.domain.Backlog;
import org.mami.tasktracker.domain.Project;
import org.mami.tasktracker.domain.Task;
import org.mami.tasktracker.domain.User;
import org.mami.tasktracker.services.BacklogService;
import org.mami.tasktracker.services.ProjectService;
import org.mami.tasktracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProjectService projectService;
    private final BacklogService backlogService;
    private final UserService userService;

    @Autowired
    public DataLoader(ProjectService projectService,
                      BacklogService backlogService,
                      UserService userService) {
        this.projectService = projectService;
        this.backlogService = backlogService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {

//        User user = new User();
//        user.setUsername("saed.mami@gmail.com");
//        user.setFullName("saed mami");
//        user.setPassword("123456");
//        this.userService.registerUser(user);
//
//
//        Project project1 = new Project();
//        project1.setName("Cool project");
//        project1.setDescription("Some cool description");
//        project1.setProjectCode("ID01");
//        this.projectService.saveOrUpdate(project1, user.getUsername());
//
//        Project project2 = new Project();
//        project2.setName("Awesome project");
//        project2.setDescription("Some awesome description");
//        project2.setProjectCode("ID02");
//        this.projectService.saveOrUpdate(project2, user.getUsername());
//
//        Task task11 = new Task();
//        task11.setSummary("Cool task 11");
//        task11.setAcceptanceCriteria("Cool ac 11");
//        task11.setStatus("TO_DO");
//        task11.setPriority(1);
//        task11.setDueDate(new Date());
//
//        Task task12 = new Task();
//        task12.setSummary("Cool task 12");
//        task12.setAcceptanceCriteria("Cool ac 12");
//        task12.setStatus("IN_PROGRESS");
//        task12.setPriority(2);
//        task12.setDueDate(new Date());
//
//        Task task13 = new Task();
//        task13.setSummary("Cool task 13");
//        task13.setAcceptanceCriteria("Cool ac 13");
//        task13.setStatus("DONE");
//        task13.setPriority(3);
//        task13.setDueDate(new Date());
//
//        this.backlogService.addTask(project1.getProjectCode(), task11);
//        this.backlogService.addTask(project1.getProjectCode(), task12);
//        this.backlogService.addTask(project1.getProjectCode(), task13);
//
//        Task task21 = new Task();
//        task21.setSummary("Awesome task 21");
//        task21.setAcceptanceCriteria("Awesome ac 13");
//        task21.setStatus("DONE");
//        task21.setPriority(2);
//        task21.setDueDate(new Date());
//
//        Task task22 = new Task();
//        task22.setSummary("Awesome task 22");
//        task22.setAcceptanceCriteria("Awesome ac 13");
//        task22.setStatus("IN_PROGRESS");
//        task22.setPriority(1);
//        task22.setDueDate(new Date());
//
//        this.backlogService.addTask(project2.getProjectCode(), task21);
//        this.backlogService.addTask(project2.getProjectCode(), task22);
    }
}
