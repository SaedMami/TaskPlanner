package org.mami.tasktracker.repositories;

import org.mami.tasktracker.domain.Backlog;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BacklogRepository extends CrudRepository<Backlog, Long> {
    Optional<Backlog> findByProjectCode(String projectCode);
}
