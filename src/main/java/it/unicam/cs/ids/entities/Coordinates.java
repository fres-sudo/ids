package it.unicam.cs.ids.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a geographical coordinates with latitude and longitude.
 * The latitude must be between -90 and 90 degrees, and the longitude must be
 * between -180 and 180 degrees.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {
    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;
}
