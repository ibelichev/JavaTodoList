package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.StatusEnum;
import org.example.model.Task;
import org.example.repository.TaskRepository;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final DateTimeFormatter dateFormatter;

    public void addTask(Task task) {
        if (task.getName().isEmpty() || task.getStatus() == null) {
            throw new IllegalArgumentException();
        }
        taskRepository.add(task);
    }

    public HashMap<Long, Task> getAll() {
        return taskRepository.getAll();
    }

    public Task getTask(Long id) {
        return taskRepository.get(id);
    }

    public Task deleteTask(Long id) {
        return taskRepository.delete(id);
    }

    public Task editTask(Long id,
                        String name,
                        String description,
                        String status,
                        String deadline
                        ) {
        Task processingTask = taskRepository.get(id);

        if (!name.isEmpty()) processingTask.setName(name);
        if (!description.isEmpty()) processingTask.setDescription(description);
        if (!status.isEmpty()) processingTask.setStatus(StatusEnum.valueOf(status));
        if (!deadline.isEmpty()) processingTask.setDeadLine(LocalDate.parse(deadline, dateFormatter));

        return taskRepository.edit(id, processingTask);
    }

    public HashMap<Long, Task> filter(StatusEnum status) {
        HashMap<Long, Task> allTasks = taskRepository.getAll();
        return (HashMap<Long, Task>) allTasks
                        .entrySet()
                        .stream()
                        .filter(
                        x -> x.getValue()
                                .getStatus()
                                .equals(status)
                        ).collect(
                                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
                        );
    }

    public LinkedHashMap<Long, Task> sort() {
        return taskRepository.getAll()
                        .entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.comparing(Task::getDeadLine)))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new
                        ));
    }
}