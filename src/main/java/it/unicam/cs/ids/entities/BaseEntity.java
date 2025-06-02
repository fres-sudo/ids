package it.unicam.cs.ids.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;

/**
 * BaseEntity is a base class for all entities in the application.
 * It provides common fields such as id, createdAt, and updatedAt.
 * It also provides methods to automatically set these fields on creation and update.
 */
@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}