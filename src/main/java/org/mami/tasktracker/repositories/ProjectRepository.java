package org.mami.tasktracker.repositories;

import org.mami.tasktracker.domain.Project;
import org.mami.tasktracker.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Optional<Project> findByProjectCodeAndUser_Username(String projectCode, String username);

    Iterable<Project> findAllByUser_Username(String username);

    Optional<Project> findByProjectCode(String projectCode);
}
