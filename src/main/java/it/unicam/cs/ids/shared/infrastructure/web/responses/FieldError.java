package it.unicam.cs.ids.shared.infrastructure.web.responses;


import lombok.Builder;

/**
 * Represents a field error in an API response.
 * This class is used to encapsulate validation errors for specific fields.
 *
 * @param field   The name of the field that caused the error.
 * @param message A descriptive message explaining the error.
 */
@Builder
public record FieldError(String field, String message) { }