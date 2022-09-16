package com.tanapp.todolist.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    public void addNewTask(Task task) {
        Optional<Task> taskOptional = taskRepository.findTaskByName(task.getName());
        if(taskOptional.isPresent()){
            throw new IllegalStateException("Task Already Present");
        }

        taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        if(!taskRepository.existsById(taskId)){
            throw new IllegalStateException("Task with id - " + taskId + " does not exist");
        }

        taskRepository.deleteById(taskId);
    }

    @Transactional
    public void updateTask(Long taskId, String name) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalStateException(
                        "Task with id - " + taskId + " does not exist"));

        if (name!=null && name.length()>0 && !Objects.equals(task.getName(), name)) {
            Optional<Task> taskOptional = taskRepository.findTaskByName(name);
            if(taskOptional.isPresent()){
                throw new IllegalStateException("Task Already Taken");
            }

            task.setName(name);
        }
    }
}
