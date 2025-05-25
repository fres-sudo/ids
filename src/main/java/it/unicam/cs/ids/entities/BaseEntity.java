package it.unicam.cs.ids.entities;

import lombok.Data;

import java.util.Date;

/**
 * Base class for all entities in the system.
 * Contains common fields such as {@code id, name, timestamps for; creation, update, deletion}.
 */
@Data
abstract class BaseEntity {
    /** Unique identifier for the entity. */
    private long id;
    /** Name of the entity. */
    private String name;
    /** Timestamp for when the entity was created. */
    private Date createdAt;
    /** Timestamp for when the entity was last updated. */
    private Date updatedAt;
    /** Timestamp for when the entity was deleted. */
    private Date deletedAt;
}
