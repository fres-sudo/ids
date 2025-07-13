package it.unicam.cs.ids.context.catalog.application.services;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.PurchaseDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.PurchaseBundleRequest;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.PurchaseProductRequest;

import java.util.List;

/**
 * Purchase Service defines the operations related to purchases.
 */
public interface PurchaseService {
    
    /**
     * Purchases a product for the specified user.
     *
     * @param productId the ID of the product to purchase
     * @param buyerId the ID of the buyer
     * @param request the purchase request details
     * @return the created PurchaseDTO
     */
    PurchaseDTO purchaseProduct(Long productId, Long buyerId, PurchaseProductRequest request);
    
    /**
     * Purchases a bundle for the specified user.
     *
     * @param bundleId the ID of the bundle to purchase
     * @param buyerId the ID of the buyer
     * @param request the purchase request details
     * @return the created PurchaseDTO
     */
    PurchaseDTO purchaseBundle(Long bundleId, Long buyerId, PurchaseBundleRequest request);
    
    /**
     * Retrieves all purchases made by a specific user.
     *
     * @param buyerId the ID of the buyer
     * @return list of PurchaseDTO objects
     */
    List<PurchaseDTO> getUserPurchases(Long buyerId);
    
    /**
     * Retrieves a specific purchase by its ID.
     *
     * @param purchaseId the ID of the purchase
     * @return the PurchaseDTO
     */
    PurchaseDTO getPurchaseById(Long purchaseId);
}