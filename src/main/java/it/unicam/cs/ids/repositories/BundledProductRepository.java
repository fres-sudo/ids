package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.models.BundledProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BundledProductRepository extends JpaRepository<BundledProduct, Long> { }