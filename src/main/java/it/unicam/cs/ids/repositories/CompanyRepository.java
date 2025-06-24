package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> { }