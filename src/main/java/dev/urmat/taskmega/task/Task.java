package dev.urmat.taskmega.task;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private Long createdAt;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private boolean completed;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now().getEpochSecond();
    }
}