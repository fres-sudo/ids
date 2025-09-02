package it.unicam.cs.ids.context.identity.domain.model;

import lombok.Getter;

/**
 * Enum representing the different types of role requests that can be made by users.
 * This enum defines the available role upgrade requests in the system.
 */
@Getter
public enum RequestType {
    /** Request to become a Certifier */
    CERTIFIER("CERTIFIER", PlatformRoles.CERTIFIER),
    /** Request to become an Animator */
    ANIMATOR("ANIMATOR", PlatformRoles.ANIMATOR);

    private final String type;
    private final PlatformRoles targetRole;

    /**
     * Constructs a RequestType with the specified type and target role.
     *
     * @param type the string representation of the request type
     * @param targetRole the platform role this request aims to achieve
     */
    RequestType(String type, PlatformRoles targetRole) {
        this.type = type;
        this.targetRole = targetRole;
    }
}