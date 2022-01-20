package io.github.mat3e.model.projection;

import io.github.mat3e.model.TaskGroup;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {

    private String description;

    private Set<GroupTaskWriteModel> tasks;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<GroupTaskWriteModel> getTasks() {
        return tasks;
    }

    public void setTasks(Set<GroupTaskWriteModel> tasks) {
        this.tasks = tasks;
    }

    public TaskGroup toGroup() {
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.setDescription(description);
        taskGroup.setTasks(
                tasks.stream()
                        .map(GroupTaskWriteModel::toTask).collect(Collectors.toSet()));
        return taskGroup;
    }
}
