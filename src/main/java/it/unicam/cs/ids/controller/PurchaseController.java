package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.services.PurchaseService;
import it.unicam.cs.ids.dto.PurchaseDTO;
import it.unicam.cs.ids.web.requests.user.PurchaseBundleRequest;
import it.unicam.cs.ids.web.requests.user.PurchaseProductRequest;
import it.unicam.cs.ids.web.responses.PurchaseBundleResponse;
import it.unicam.cs.ids.web.responses.PurchaseProductResponse;
import it.unicam.cs.ids.services.AuthService;
import it.unicam.cs.ids.model.User;
import it.unicam.cs.ids.security.user.AppUserPrincipal;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.infrastructure.api.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling purchase-related operations.
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/purchase")
public class PurchaseController {
    /** Service for handling purchase operations */
    private final PurchaseService purchaseService;
    /** Service for handling authentication and authorization */
    private final AuthService authService;
    /** Factory for creating API responses */
    private final ApiResponseFactory responseFactory;

    /**
     * Endpoint to purchase a product.
     *
     * @param request the request containing purchase details
     * @param productId the ID of the product to purchase
     * @return a response containing the purchase details
     */
    @PostMapping("/product/{productId}")
    public ApiResponse<PurchaseProductResponse> purchaseProduct(
            @RequestBody PurchaseProductRequest request,
            @PathVariable Long productId
    ) {
        User authenticatedUser = authService.getAuthenticatedUser();
        PurchaseDTO purchase = purchaseService.purchaseProduct(productId, authenticatedUser.getId(), request);
        
        PurchaseProductResponse response = PurchaseProductResponse.builder()
                .purchase(purchase)
                .message("Product purchased successfully")
                .success(true)
                .build();
        
        return responseFactory.createSuccessResponse(
                Messages.Success.PURCHASE_COMPLETED,
                response
        );
    }

    /**
     * Endpoint to purchase a bundle.
     *
     * @param request the request containing purchase details
     * @param bundleId the ID of the bundle to purchase
     * @return a response containing the purchase details
     */
    @PostMapping("/bundle/{bundleId}")
    public ApiResponse<PurchaseBundleResponse> purchaseBundle(
            @RequestBody PurchaseBundleRequest request,
            @PathVariable Long bundleId
    ) {
        User authenticatedUser = authService.getAuthenticatedUser();
        PurchaseDTO purchase = purchaseService.purchaseBundle(bundleId, authenticatedUser.getId(), request);
        
        PurchaseBundleResponse response = PurchaseBundleResponse.builder()
                .purchase(purchase)
                .message("Bundle purchased successfully")
                .success(true)
                .build();
        
        return responseFactory.createSuccessResponse(
                Messages.Success.PURCHASE_COMPLETED,
                response
        );
    }

    /**
     * Endpoint to retrieve all purchases made by the authenticated user.
     *
     * @param pageNo the page number for pagination
     * @param pageSize the size of each page
     * @param sortBy the field to sort by
     * @param principal the authenticated user's principal
     * @return a paginated list of purchases made by the user
     */
    @GetMapping("/user")
    public Page<PurchaseDTO> getUserPurchases(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) { //FIXME: don't use AppUserPrincipal
       return purchaseService.getUserPurchases(principal.getId(), pageNo, pageSize, sortBy);
    }

    /**
     * Endpoint to retrieve a specific purchase by its ID.
     *
     * @param purchaseId the ID of the purchase to retrieve
     * @param principal the authenticated user's principal
     * @return the details of the specified purchase
     */
    @GetMapping("/{purchaseId}")
    public ApiResponse<PurchaseDTO> getPurchase(
            @PathVariable Long purchaseId,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) {
        PurchaseDTO purchase = purchaseService.getPurchaseById(purchaseId);
        
        return responseFactory.createSuccessResponse(
                Messages.Success.PURCHASE_RETRIEVED,
                purchase
        );
    }
}
