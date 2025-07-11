package it.unicam.cs.ids.shared.kernel.exceptions.auth;

import lombok.NonNull;

public class NotFound extends RuntimeException {

    public NotFound(@NonNull String resource) {
        super("\" resource \"" + " not found");
    }
}
