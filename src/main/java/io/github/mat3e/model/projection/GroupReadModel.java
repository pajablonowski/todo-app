package io.github.mat3e.model.projection;

import io.github.mat3e.model.Task;
import io.github.mat3e.model.TaskGroup;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupReadModel {

    private String description;

    /**
     * deadline from the latest task of the group
     */
    private LocalDateTime deadline;

    private Set<GroupTaskReadModel> tasks;

    public GroupReadModel(TaskGroup source) {
        this.description = source.getDescription();
        source.getTasks()
                .stream()
                .map(Task::getDeadline)
                .max(LocalDateTime::compareTo)
                .ifPresent(date -> this.deadline = date);
        this.tasks = source
                        .getTasks()
                        .stream()
                        .map(GroupTaskReadModel::new)
                        .collect(Collectors.toSet());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Set<GroupTaskReadModel> getTasks() {
        return tasks;
    }

    public void setTasks(Set<GroupTaskReadModel> tasks) {
        this.tasks = tasks;
    }
}
