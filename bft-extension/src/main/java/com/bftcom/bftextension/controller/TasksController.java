package com.bftcom.bftextension.controller;

import com.bftcom.bftextension.model.Task;
import com.bftcom.bftextension.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

/**
 * @author Markitanov Vadim
 * @since 21.09.2022
 */
@RestController
@RequestMapping("/tasks")
public class TasksController {
    private TaskRepository taskRepository;

    @GetMapping
    Iterable<Task> getTasks() {
        return taskRepository.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    Task postTask(@RequestBody Task task) {
        System.out.println("Post task: " + task);
        if (task.getPriority() == null) {
            task.setPriority(0);
        }

        taskRepository.save(task);
        return task;
    }

    @DeleteMapping("/{id}")
    boolean deleteTask(@PathVariable long id) {
        System.out.println("Delete: " + id);
        taskRepository.deleteAllById(Collections.singletonList(id));
        return true;
    }

    @GetMapping("/{id}")
    Optional<Task> getTaskById(@PathVariable long id) {
        System.out.println("Get" + id);
        taskRepository.deleteAllById(Collections.singletonList(id));

        return Optional.empty();
    }

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /*



    @PutMapping("/{id}")
    ResponseEntity<Task> putTask(@PathVariable long id, @RequestBody Task task) {
        int taskIndex = -1;

        for (Task t : tasks) {
            if (t.getId() == id) {
                taskIndex = tasks.indexOf(t);
                tasks.set(taskIndex, task);
            }
        }

        return (taskIndex == -1) ?
                new ResponseEntity<>(postTask(task), HttpStatus.CREATED) :
                new ResponseEntity<>(task, HttpStatus.OK);
    }

    */

}
