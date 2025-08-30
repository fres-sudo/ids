package it.unicam.cs.ids.context.catalog.domain.repositories;

import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BundleRepository extends JpaRepository<Bundle, Long>, JpaSpecificationExecutor<Bundle> {
    @NotNull
    @EntityGraph(attributePaths = {
            "products.product",  // BundledProduct + its Product
            "distributor",
            "tags"
    })
    Optional<Bundle> findById(@NotNull Long id);
}