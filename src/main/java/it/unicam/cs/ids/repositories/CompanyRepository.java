package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
    Optional<Company> findByEmail(String email);

    boolean existsByEmail(String email);

    // debugging
    default Company findByEmailOrThrow(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found in DB: " + email));
    }
}