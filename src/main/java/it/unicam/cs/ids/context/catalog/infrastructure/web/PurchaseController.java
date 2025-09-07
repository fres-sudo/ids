package it.unicam.cs.ids.context.catalog.infrastructure.web;

import it.unicam.cs.ids.context.catalog.application.services.PurchaseService;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.PurchaseDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.PurchaseBundleRequest;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.PurchaseProductRequest;
import it.unicam.cs.ids.context.identity.application.services.AuthService;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.infrastructure.security.user.AppUserPrincipal;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ApiResponse<PurchaseDTO<ProductDTO>> purchaseProduct(
            @RequestBody PurchaseProductRequest request,
            @PathVariable Long productId
    ) {
        User authenticatedUser = authService.getAuthenticatedUser();
        PurchaseDTO<ProductDTO> purchase = purchaseService.purchaseProduct(productId, authenticatedUser.getId(), request);

        return responseFactory.createSuccessResponse(
                Messages.Success.PURCHASE_COMPLETED,
                purchase
        );
    }

    @PostMapping("/bundle/{bundleId}")
    public ApiResponse<PurchaseDTO<BundleDTO>> purchaseBundle(
            @RequestBody PurchaseBundleRequest request,
            @PathVariable Long bundleId
    ) {
        User authenticatedUser = authService.getAuthenticatedUser();
        PurchaseDTO<BundleDTO> purchase = purchaseService.purchaseBundle(bundleId, authenticatedUser.getId(), request);
        
        return responseFactory.createSuccessResponse(
                Messages.Success.PURCHASE_COMPLETED,
                purchase
        );
    }

    //TODO: Add authorization checks for the following endpoints if needed
    @GetMapping("/user")
    public Page<PurchaseDTO<?>> getUserPurchases(
            @PageableDefault() Pageable pageable,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) {
       return purchaseService.getUserPurchases(principal.getId(), pageable);
    }
}
