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

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final ApiResponseFactory responseFactory;
    private final AuthService authService;

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

    @GetMapping("/user")
    public Page<PurchaseDTO> getUserPurchases(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) {
       return purchaseService.getUserPurchases(principal.getId(), pageNo, pageSize, sortBy);
    }

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
