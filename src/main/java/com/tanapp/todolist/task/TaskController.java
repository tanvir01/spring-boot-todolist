package com.tanapp.todolist.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping(path = "{taskId}")
    public ResponseEntity<Object> getTaskById(@PathVariable("taskId") Long taskId) {
        Optional<Task> taskOptional = taskService.getTaskById(taskId);
        if (!taskOptional.isPresent()) {
            return new ResponseEntity<>("Task Not Found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(taskOptional, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody Task task) {
        taskService.addNewTask(task);
        return new ResponseEntity<>("Task is created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{taskId}")
    public ResponseEntity<Object> deleteTask(@PathVariable("taskId") Long taskId){
        taskService.deleteTask(taskId);
        return new ResponseEntity<>("Task is deleted successfully", HttpStatus.OK);
    }

    @PatchMapping(path = "{taskId}")
    public ResponseEntity<Object> updateTask(@PathVariable("taskId") Long taskId,
                             @RequestParam(required = false) String name){
        taskService.updateTask(taskId, name);
        return new ResponseEntity<>("Task is updated successfully", HttpStatus.OK);
    }
}
