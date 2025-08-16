package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.model.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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