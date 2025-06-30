package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByEmail(String email);
    boolean existsByEmail(String email);

    // debugging
    default Company findByEmailOrThrow(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found in DB: " + email));
    }
}