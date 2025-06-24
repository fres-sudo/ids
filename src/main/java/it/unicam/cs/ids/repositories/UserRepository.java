package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // debugging
    default User findByEmailOrThrow(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found in DB: " + email));
    }
}