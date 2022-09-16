package com.tanapp.todolist.task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindTaskByName() {
        //given
        String name = "Invest";
        Task task = new Task(name);
        underTest.save(task);
        //when
        Optional<Task> taskByName = underTest.findTaskByName(name);
        //then
        assertThat(taskByName.isPresent()).isTrue();
    }

    @Test
    void shouldNotFindTaskByName() {
        //given
        String name = "Invest";
        //when
        Optional<Task> taskByName = underTest.findTaskByName(name);
        //then
        assertThat(taskByName.isPresent()).isFalse();
    }
}