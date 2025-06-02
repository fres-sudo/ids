package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.entities.Bundle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BundleRepository extends JpaRepository<Bundle, Long> { }