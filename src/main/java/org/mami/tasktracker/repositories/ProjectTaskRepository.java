package org.mami.tasktracker.repositories;

import org.mami.tasktracker.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<Task, Long> {

}
