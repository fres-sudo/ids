package it.unicam.cs.ids.shared.infrastructure.persistence;

import it.unicam.cs.ids.models.User;
import it.unicam.cs.ids.repositories.UserRepository;
import it.unicam.cs.ids.shared.kernel.enums.PlatformRoles;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Utility class for finding entities by ID in a JpaRepository.
 * Provides a method to find an entity by its ID and throw an exception if not found.
 */
public final class Finder {
    // Private constructor to prevent instantiation
    private Finder() {}

    /**
     * Finds an entity by its ID in the given JpaRepository.
     * If the entity is not found, throws an EntityNotFoundException with the provided error message.
     *
     * @param repository the JpaRepository to search in
     * @param id the ID of the entity to find
     * @param errorMessage the error message to use if the entity is not found
     * @param <T> the type of the entity
     * @param <ID> the type of the entity's ID
     * @return the found entity
     * @throws EntityNotFoundException if no entity with the given ID is found
     */
    public static <T, ID> T findByIdOrThrow(JpaRepository<T, ID> repository, ID id, String errorMessage) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(errorMessage));
    }

    /**
     * Finds a user's role by their ID in the given UserRepository.
     *
     * @param repository the UserRepository to search in
     * @param id the ID of the user to find
     * @return the role of the user as a String
     * @throws EntityNotFoundException if no user with the given ID is found
     */
    public static PlatformRoles getUserRole(UserRepository repository, Long id) {
        return repository.findById(id)
                .map(User::getRole)
                .orElse(null);
    }
}
