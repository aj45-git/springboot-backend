package com.taskapi.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskapi.springboot.model.Task;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
