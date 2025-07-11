package it.unicam.cs.ids.context.certification.domain.repositories;

import it.unicam.cs.ids.context.certification.domain.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> { }