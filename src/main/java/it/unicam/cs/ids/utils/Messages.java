package it.unicam.cs.ids.utils;


/**
 * Utility class for managing messages used across the application.
 * This class contains nested interfaces for categorizing messages.
 * @see Success
 * @see Error
 * @see Warning
 * @see Info
 */
public final class Messages {
    /** prevent instantiation */
    private Messages() {}
    public interface Success {
        String USER_REGISTERED = "User registered successfully";
        String USER_RETRIEVED = "User retrieved successfully";
        String USER_LOGGED_IN = "User logged in successfully";
        String USER_UPDATED = "User updated successfully";
        String USER_DELETED = "User deleted successfully";

        String CERTIFIER_REGISTERED = "Certifier registered successfully";
        String CERTIFIER_RETRIEVED = "Certifier retrieved successfully";
        String CERTIFIER_UPDATED = "Certifier updated successfully";
        String CERTIFIER_DELETED = "Certifier deleted successfully";

        String PRODUCT_CREATED = "Product created successfully";
        String PRODUCT_RETRIEVED = "Product retrieved successfully";

        String CERTIFICATE_CREATED = "Certificate created successfully";
        String CERTIFICATE_RETRIEVED = "Certificate retrieved successfully";

        String BUNDLE_CREATED = "Bundle created successfully";
        String BUNDLE_RETRIEVED = "Bundle retrieved successfully";

        String COMPANY_REGISTERED = "Company registered successfully";
        String COMPANY_RETRIEVED = "Company retrieved successfully";
        String COMPANY_UPDATED = "Company updated successfully";
        String COMPANY_DELETED = "Company deleted successfully";
    }

    public interface Error {
        String PRODUCT_NOT_FOUND = "Product not found";
        String CERTIFICATE_NOT_FOUND = "Certificate not found";

        String INVALID_REQUEST = "Invalid request data";
        String INTERNAL_ERROR = "An internal error occurred";
    }

    public interface Warning {
        String DEPRECATED_FEATURE = "This feature is deprecated and may be removed in future versions";
        String UNSUPPORTED_OPERATION = "This operation is not supported";

        String CANT_BE_BLANK = " cannot be blank";
    }

    public interface Info {

    }

    public interface Auth {
        String UNAUTHORIZED_ACCESS = "Unauthorized access";
        String USER_NOT_FOUND = "User not found";
        String INVALID_PASSWORD = "Invalid password";
        String NO_UNIQUE_EMAIL = "Email already in use, choose a unique one";
        String INVALID_COMPANY_DELETE_REQUEST = "Invalid company delete request. Please check your credentials and try again.";
    }
}
