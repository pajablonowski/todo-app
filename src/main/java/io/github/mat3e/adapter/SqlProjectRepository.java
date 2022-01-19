package io.github.mat3e.adapter;

import io.github.mat3e.model.Project;
import io.github.mat3e.model.repositories.ProjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Long> {

    @Override
    @Query("select distinct p from Project p join fetch p.taskGroups g join fetch p.projectSteps")
    List<Project> getAll();
}
