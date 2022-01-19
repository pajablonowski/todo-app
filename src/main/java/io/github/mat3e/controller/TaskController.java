package io.github.mat3e.controller;

import io.github.mat3e.model.Task;
import io.github.mat3e.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

//@Controller
@RestController
class TaskController {

    public static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    //    @RequestMapping(method = RequestMethod.GET, value = "/tasks", params = {"!sort", "!page", "!size"})
    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> /*List<Task>*/ readAllTasks() {
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
        /*return repository.findAll();*/
    }

    //    @RequestMapping(method = RequestMethod.GET, value = "/tasks")
    @GetMapping(value = "/tasks")
    ResponseEntity<List<Task>> /*List<Task>*/ readAllTasks(Pageable page) {
        logger.info("Custom pager");
        return ResponseEntity.ok(repository.findAll(page).getContent());
        /*return repository.findAll();*/
    }

    //    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{id}")
    @GetMapping(value = "/tasks/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {

        return repository.findById(id)
                .map(task -> ResponseEntity.ok(task))
                .orElse(ResponseEntity.notFound().build());

    }

    //    @RequestMapping(method = RequestMethod.PUT, value = "/tasks/{id}")
    @Transactional
    @PutMapping(value = "/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsByIdS(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).ifPresent(task -> task.updateFrom(toUpdate));
        logger.info("Zapisano dane");

        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping(value = "/tasks/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsByIdS(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> task.setDone(!task.isDone()));

        return ResponseEntity.noContent().build();
    }


    //    @RequestMapping(method = RequestMethod.POST, value = "/tasks")
    @PostMapping(value = "/tasks")
    ResponseEntity<Task> createTask(@RequestBody @Valid Task toCreate) {

        Task task = repository.save(toCreate);

        return ResponseEntity.created(URI.create("/" + task.getId())).body(task);

    }

}
