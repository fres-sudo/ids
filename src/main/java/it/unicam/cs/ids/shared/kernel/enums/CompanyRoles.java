package it.unicam.cs.ids.shared.kernel.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * CompanyRoles defines the roles available for companies in the platform.
 * Each role represents a different function that a company can perform.
 */
public enum CompanyRoles {
    /** Represents a company that produces products. */
    PRODUCER("PRODUCER"),
    /** Represents a company that transforms raw materials into products. */
    TRANSFORMER("TRANSFORMER"),
    /** Represents a company that single & bundled products */
    DISTRIBUTOR("DISTRIBUTOR");

    /** The string representation of the company role. */
    private final String companyRole;

    /**
     * Constructs a CompanyRoles enum with the specified company role string.
     *
     * @param companyRole the string representation of the company role
     */
    CompanyRoles(String companyRole) {
        this.companyRole = companyRole;
    }

    /**
     * Allows case-insensitive deserialization from string.
     * Supports @PathVariable and JSON input mapping.
     */
    @JsonCreator
    public static CompanyRoles fromString(String value) {
        if (value == null) return CompanyRoles.PRODUCER;

        for (CompanyRoles role : CompanyRoles.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid company role: " + value);
    }

    @Override
    public String toString() {
        return this.companyRole;
    }

    /**
     * Returns an array of all company roles as strings.
     * @return an array of company role strings
     */
    public static String[] getCompanyRoles() {
        String[] roles = new String[CompanyRoles.values().length];
        for (int i = 0; i < CompanyRoles.values().length; i++) {
            roles[i] = CompanyRoles.values()[i].toString();
        }
        return roles;
    }
}
