package it.unicam.cs.ids.shared.kernel.enums;


import lombok.Getter;

/**
 * Represents the sort direction for sorting operations.
 * The valid directions are:
 * <li> <b>ASC</b>: Ascending order.</li>
 * <li> <b>DESC</b>: Descending order.</li>
 */
public enum SortDirection {
    /** Ascending sort direction. */
    ASC("asc"),
    /** Descending sort direction. */
    DESC("desc");

    /** The string representation of the sort direction. */
    @Getter private final String value;

    /**
     * Constructor for the {@link SortDirection} enum.
     * @param value The string representation of the sort direction.
     */
    SortDirection(String value) {
        this.value = value;
    }

    /**
     * Checks if the provided string is a valid sort direction.
     * @param direction The string representation of the sort direction to check.
     * @return true if the direction is valid, false otherwise.
     */
    public static boolean isValid(String direction) {
        return ASC.getValue().equalsIgnoreCase(direction) || DESC.getValue().equalsIgnoreCase(direction);
    }
}
