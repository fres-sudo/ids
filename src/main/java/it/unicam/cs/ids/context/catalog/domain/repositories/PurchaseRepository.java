package it.unicam.cs.ids.context.catalog.domain.repositories;

import it.unicam.cs.ids.context.catalog.domain.model.Purchase;
import it.unicam.cs.ids.context.identity.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>, JpaSpecificationExecutor<Purchase> {
    
    List<Purchase> findByBuyerOrderByPurchaseDateDesc(User buyer);
    
    List<Purchase> findByProductIdOrderByPurchaseDateDesc(Long productId);
    
    List<Purchase> findByBundleIdOrderByPurchaseDateDesc(Long bundleId);
    
    @Query("SELECT p FROM Purchase p WHERE p.buyer.id = :buyerId ORDER BY p.purchaseDate DESC")
    List<Purchase> findPurchasesByBuyerId(@Param("buyerId") Long buyerId);
}