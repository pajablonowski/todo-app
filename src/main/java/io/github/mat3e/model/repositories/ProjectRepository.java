package io.github.mat3e.model.repositories;

import io.github.mat3e.model.Project;
import io.github.mat3e.model.TaskGroup;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    List<Project> getAll();

    Optional<Project> findById(Integer id);

    Project save(Project entity);

}
