package com.tanapp.todolist.task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    private TaskService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TaskService(taskRepository);
    }

    @Test
    void canGetAllTasks() {
        //when
        underTest.getAllTasks();
        //then
        verify(taskRepository).findAll();
    }

    @Test
    void getTaskById() {
        //when
        underTest.getTaskById(1L);
        //then
        verify(taskRepository).findById(1L);
    }

    @Test
    void canAddNewTask() {
        // given
        Task task = new Task(
                "Code"
        );
        // when
        underTest.addNewTask(task);
        // then
        ArgumentCaptor<Task> taskArgumentCaptor =
                ArgumentCaptor.forClass(Task.class);

        verify(taskRepository)
                .save(taskArgumentCaptor.capture());

        Task capturedTask = taskArgumentCaptor.getValue();

        assertThat(capturedTask).isEqualTo(task);
    }

    @Test
    void deleteTask() {
        // given
        long id = 10;
        given(taskRepository.existsById(id))
                .willReturn(true);
        // when
        underTest.deleteTask(id);
        // then
        verify(taskRepository).deleteById(id);
    }

    @Test
    void willThrowWhenDeleteStudentNotFound() {
        // given
        long id = 10;
        given(taskRepository.existsById(id))
                .willReturn(false);
        // when
        // then
        assertThatThrownBy(() -> underTest.deleteTask(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Task with id - " + id + " does not exist");

        verify(taskRepository, never()).deleteById(any());
    }

    @Test
    void updateTask() {
        // given
        long id = 10;
        Task task = new Task();
        task.setId(id);
        task.setName("random");
        given(taskRepository.findById(id)).willReturn(Optional.of(new Task()));
        // when
        String newName = "sample";
        underTest.updateTask(id, newName);
        // then
        verify(taskRepository).findTaskByName(newName);
    }
}