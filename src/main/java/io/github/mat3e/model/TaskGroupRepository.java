package io.github.mat3e.model;

import java.util.List;
import java.util.Optional;

public interface TaskGroupRepository {

    List<TaskGroup> getAll();

    Optional<TaskGroup> findById();

    TaskGroup save(TaskGroup entity);

}
