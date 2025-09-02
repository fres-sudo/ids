package it.unicam.cs.ids.context.company.domain.repositories;

import it.unicam.cs.ids.context.company.domain.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
    Optional<Company> findByEmail(String email);
    
    Optional<Company> findByVat(String vat);

    boolean existsByEmail(String email);
    
    boolean existsByVat(String vat);
}