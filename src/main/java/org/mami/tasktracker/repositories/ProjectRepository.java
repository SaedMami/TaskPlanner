package org.mami.tasktracker.repositories;

import org.mami.tasktracker.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Optional<Project> findByProjectCode(String projectCode);

}
