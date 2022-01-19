package io.github.mat3e.adapter;

import io.github.mat3e.model.TaskGroup;
import io.github.mat3e.model.repositories.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup, Long> {

    @Override
    @Query("select distinct g from TaskGroup g join fetch g.tasks")
    List<TaskGroup> getAll();

    @Override
    boolean existsByDoneIsFalseAndProject_Id(Integer projectId);
}
