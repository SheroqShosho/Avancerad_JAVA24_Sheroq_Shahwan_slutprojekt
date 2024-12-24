package com.example.TaskServer;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GRUDInterface {
    ResponseEntity<List<Task>> getAllTasks();
    ResponseEntity<Task> createTask();
    ResponseEntity<Task> updateTask();
    ResponseEntity<Task> deleteTask();
}
