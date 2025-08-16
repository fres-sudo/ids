package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.models.Bundle;
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