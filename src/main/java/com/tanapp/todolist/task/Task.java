package com.tanapp.todolist.task;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Task {
    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "task_sequence",
            strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    @Column(nullable = false)
    private String name;
    private LocalDateTime createDateTime = LocalDateTime.now();
    private LocalDateTime updateDateTime = LocalDateTime.now();

    public Task(String name) {
        this.name = name;
    }

    @PreUpdate
    public void setLastUpdate() {  this.updateDateTime = LocalDateTime.now(); }
}
