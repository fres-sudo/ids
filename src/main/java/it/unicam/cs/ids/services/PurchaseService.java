package it.unicam.cs.ids.services;

import it.unicam.cs.ids.dto.PurchaseDTO;
import it.unicam.cs.ids.web.requests.user.PurchaseBundleRequest;
import it.unicam.cs.ids.web.requests.user.PurchaseProductRequest;
import org.springframework.data.domain.Page;

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
    Page<PurchaseDTO> getUserPurchases(Long buyerId, Integer pageNo, Integer pageSize, String sortBy);
    
    /**
     * Retrieves a specific purchase by its ID.
     *
     * @param purchaseId the ID of the purchase
     * @return the PurchaseDTO
     */
    PurchaseDTO getPurchaseById(Long purchaseId);
}