package it.unicam.cs.ids.enums;


/**
 * Represents a geographical coordinates with latitude and longitude.
 * The latitude must be between -90 and 90 degrees, and the longitude must be
 * between -180 and 180 degrees.
 */
public record Coordinates(
        double latitude,
        double longitude
) {
    public Coordinates {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees.");
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees.");
        }
    }
}
