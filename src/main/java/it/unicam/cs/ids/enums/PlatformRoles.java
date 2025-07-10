package it.unicam.cs.ids.enums;


import lombok.Getter;

/**
 * PlatformRoles defines the roles available in the platform.
 */
@Getter
public enum PlatformRoles {
    /** Represents the administrator role with full access to the platform. */
    ADMIN("admin"),
    /** Represents a user with the ability to manage certifications. */
    CERTIFIER("certifier"),
    /** Represents a company that can upload and manage products. */
    COMPANY("company"),
    /** Represents a Signed-in user who can browse and purchase products. */
    USER("user");

    /** The string representation of the role. */
    private final String role;

    /**
     * Constructs a PlatformRoles enum with the specified role string.
     *
     * @param role the string representation of the role
     */
    PlatformRoles(String role) {
        this.role = role;
    }

}