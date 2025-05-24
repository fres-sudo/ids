package it.unicam.cs.ids.enums;


import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents the sort direction for sorting operations.
 * The valid directions are:
 * <li> <b>ASC</b>: Ascending order.</li>
 * <li> <b>DESC</b>: Descending order.</li>
 */
public enum SortDirection {
    ASC("asc"),
    DESC("desc");

    /** The string representation of the sort direction. */
    @Getter private final String value;

    SortDirection(String value) {
        this.value = value;
    }

     /////////// For quick access to valid sort directions ///////////
    /** A set of valid sort direction strings for quick access. */
    private static final Set<String> VALID_DIRECTIONS = Stream.of(values())
            .map(SortDirection::getValue)
            .collect(Collectors.toUnmodifiableSet()
    );

    /**
     * Checks if the provided string is a valid sort direction <b>(case-insensitive)</b>.
     * @param direction The direction to check.
     * @return true if the direction is valid, false otherwise.
     */
    public static boolean isValid(String direction) {
        if (direction == null) return false;
        return VALID_DIRECTIONS.contains(direction.toLowerCase());
    }

    /**
     * Parses a string into a SortDirection <b>(case-insensitive)</b>.
     * @param direction The direction string {@code (e.g., "ASC", "desc")}.
     * @return The corresponding SortDirection, or null if invalid.
     */
    public static SortDirection fromString(String direction) {
        if (direction == null) return null;
        for (SortDirection sortDir : values()) {
            if (sortDir.value.equalsIgnoreCase(direction)) return sortDir;
        }
        return null;
    }
}
