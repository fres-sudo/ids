package it.unicam.cs.ids.context.catalog.domain.repositories;

import it.unicam.cs.ids.context.catalog.domain.model.BundledProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BundledProductRepository extends JpaRepository<BundledProduct, Long> { }