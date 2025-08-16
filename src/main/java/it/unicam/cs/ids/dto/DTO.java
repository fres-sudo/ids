package it.unicam.cs.ids.dto;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Base class for all Data Transfer Objects (DTOs).
 * DTOs are used to transfer data between different layers of the application.
 * They are typically used to encapsulate data that is sent over the network or between different components.
 * This class can be extended by other DTO classes to provide common functionality or properties.
 */
@NoArgsConstructor
@SuperBuilder
public abstract class DTO {}
