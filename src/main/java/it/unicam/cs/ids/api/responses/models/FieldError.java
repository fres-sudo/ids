package it.unicam.cs.ids.api.responses.models;


/**
 * Represents a field error in an API response.
 * This class is used to encapsulate validation errors for specific fields.
 *
 * @param field   The name of the field that caused the error.
 * @param message A descriptive message explaining the error.
 */
public record FieldError(String field, String message) { }