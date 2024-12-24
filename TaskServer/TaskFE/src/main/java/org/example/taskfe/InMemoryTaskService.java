package org.example.taskfe;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskService {

    private final List<Task> tasks = new ArrayList<>();
    private int currentId = 1;


    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }


    public void addTask(Task task) {
        task.setId(currentId++);
        tasks.add(task);

    }

    public void updateTask(Task task) {
        Task existingTask = tasks.stream()
                .filter(t -> t.getId() == task.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setComplete(task.isCompleted());

    }


    public void deleteTask(int id) {
        tasks.removeIf(t -> t.getId() == id);

    }
}
