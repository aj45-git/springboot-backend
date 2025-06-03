package com.taskapi.springboot.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskapi.springboot.model.Task;
import com.taskapi.springboot.repository.TaskRepository;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class TaskController {

	    private final TaskRepository repository;

	    public TaskController(TaskRepository repository) {
	        this.repository = repository;
	    }

	    // POST /tasks – create
	    @PostMapping("/tasks")
	    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
	        Task saved = repository.save(task);
	        return ResponseEntity.status(201).body(saved); // 201 Created
	    }

	    // GET /tasks – list all
	    @GetMapping("/tasks")
	    public List<Task> getAllTasks() {
	        return repository.findAll();
	    }

	    // PUT /tasks/{id} – update
	    @PutMapping("/tasks/{id}/status")
	    public ResponseEntity<Task> updateTask(@PathVariable UUID id, @Valid @RequestBody Task updatedTask) {
	        return repository.findById(id)
	            .map(existing -> {
	                existing.setTitle(updatedTask.getTitle());
	                existing.setDescription(updatedTask.getDescription());
	                existing.setStatus(updatedTask.getStatus());
	                return ResponseEntity.ok(repository.save(existing));
	            })
	            .orElse(ResponseEntity.notFound().build()); // 404 if not found
	    }

	    // DELETE /tasks/{id}
	    @DeleteMapping("/tasks/{id}")
	    public ResponseEntity<Boolean> deleteTask(@PathVariable UUID id) {
	        return repository.findById(id)
	            .map(task -> {
	                repository.delete(task);
	                return ResponseEntity.ok(true);
	            })
	            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(false));
	    }
}
