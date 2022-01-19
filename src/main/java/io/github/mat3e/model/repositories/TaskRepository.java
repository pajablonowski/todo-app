package io.github.mat3e.model.repositories;

import io.github.mat3e.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    List<Task> findAll();

    Page<Task> findAll(Pageable page);

    Optional<Task> findById(Integer id);

    List<Task> findAllByDone(boolean done);

    boolean existsByIdS(Integer id);

    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);

    Task save(Task entity);


}
