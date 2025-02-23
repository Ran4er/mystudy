package ru.ifmo.se.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "attempts", schema = "s373336")
public class AttemptEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @NotNull(message = "X coordinate cannot be null")
    @Column(name = "x", nullable = false)
    private double x;

    @NotNull(message = "Y coordinate cannot be null")
    @Column(name = "y", nullable = false)
    private double y;

    @NotNull(message = "R coordinate cannot be null")
    @Column(name = "r", nullable = false)
    private double r;

    @NotNull(message = "Result cannot be null")
    @Column(name = "result", nullable = false)
    private boolean result;

    @NotNull(message = "Created at cannot be null")
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @NotNull(message = "Execution time cannot be null")
    @Column(name = "execution_time", nullable = false)
    private Long executionTime;

    public AttemptEntity(double x, double y, double r, boolean result, Date createdAt, Long executionTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.createdAt = createdAt;
        this.executionTime = executionTime;
    }
}
