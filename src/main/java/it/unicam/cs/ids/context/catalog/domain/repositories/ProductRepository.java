package it.unicam.cs.ids.context.catalog.domain.repositories;

import it.unicam.cs.ids.context.catalog.domain.model.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @NotNull
    @EntityGraph(attributePaths = {
            "producer",
            "creator",
            "distributors",
            "transformers",
            "certificates",
            "imageUrls",
            "tags"
    })
    Optional<Product> findById(@NotNull Long id);
}