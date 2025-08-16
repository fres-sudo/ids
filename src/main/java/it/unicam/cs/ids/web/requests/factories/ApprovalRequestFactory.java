package it.unicam.cs.ids.web.requests.factories;


/**
 * ApprovalRequestFactory is responsible for creating and submitting approval requests for products.
 * It encapsulates the logic for submitting an approval request based on the product ID and creator ID
 */
public interface ApprovalRequestFactory {
    /**
     * Submits an approval request for a product.
     *
     * @param productId the ID of the product to be approved
     * @param creatorId the ID of the user who created the product
     */
    void submit(Long productId, Long creatorId);
}
