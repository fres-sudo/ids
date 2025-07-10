package it.unicam.cs.ids.exceptions.auth;

import it.unicam.cs.ids.utils.Messages;

/**
 * NotUniqueEmail is thrown when an attempt is made to register or update a user with an email
 * that already exists in the system.
 * This exception indicates that the email must be unique across all users.
 */
public class NotUniqueEmail extends RuntimeException {

    /**
     * Constructs a new NotUniqueEmail exception with the default message.
     */
    public NotUniqueEmail() {
        super(Messages.Auth.NO_UNIQUE_EMAIL);
    }

    /**
     * Constructs a new NotUniqueEmail exception with the specified detail message.
     *
     * @param message the detail message
     */
    public NotUniqueEmail(String message) {
        super(message != null ? message : Messages.Auth.NO_UNIQUE_EMAIL);
    }


    /**
     * Constructs a new NotUniqueEmail exception with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public NotUniqueEmail(Throwable cause) {
        super(Messages.Auth.NO_UNIQUE_EMAIL, cause);
    }
}
