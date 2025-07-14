package it.unicam.cs.ids.context.catalog.infrastructure.web;

import it.unicam.cs.ids.context.catalog.application.services.PurchaseService;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.PurchaseDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.PurchaseBundleRequest;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.PurchaseProductRequest;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.responses.PurchaseBundleResponse;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.responses.PurchaseProductResponse;
import it.unicam.cs.ids.context.identity.infrastructure.security.user.AppUserPrincipal;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final ApiResponseFactory responseFactory;

    @PostMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<PurchaseProductResponse>> purchaseProduct(
            @RequestBody PurchaseProductRequest request,
            @PathVariable Long productId,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) {
        Long buyerId = principal.getId();
        PurchaseDTO purchase = purchaseService.purchaseProduct(productId, buyerId, request);
        
        PurchaseProductResponse response = PurchaseProductResponse.builder()
                .purchase(purchase)
                .message("Product purchased successfully")
                .success(true)
                .build();
        
        ApiResponse<PurchaseProductResponse> apiResponse = responseFactory.createSuccessResponse(
                Messages.Success.PURCHASE_COMPLETED,
                response
        );
        
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/bundle/{bundleId}")
    public ResponseEntity<ApiResponse<PurchaseBundleResponse>> purchaseBundle(
            @RequestBody PurchaseBundleRequest request,
            @PathVariable Long bundleId,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) {
        Long buyerId = principal.getId();
        PurchaseDTO purchase = purchaseService.purchaseBundle(bundleId, buyerId, request);
        
        PurchaseBundleResponse response = PurchaseBundleResponse.builder()
                .purchase(purchase)
                .message("Bundle purchased successfully")
                .success(true)
                .build();
        
        ApiResponse<PurchaseBundleResponse> apiResponse = responseFactory.createSuccessResponse(
                Messages.Success.PURCHASE_COMPLETED,
                response
        );
        
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<Page<PurchaseDTO>> getUserPurchases(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) {
        Page<PurchaseDTO> purchases = purchaseService.getUserPurchases(principal.getId(), pageNo, pageSize, sortBy);

        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/{purchaseId}")
    public ResponseEntity<ApiResponse<PurchaseDTO>> getPurchase(
            @PathVariable Long purchaseId,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) {
        PurchaseDTO purchase = purchaseService.getPurchaseById(purchaseId);
        
        ApiResponse<PurchaseDTO> apiResponse = responseFactory.createSuccessResponse(
                Messages.Success.PURCHASE_RETRIEVED,
                purchase
        );
        
        return ResponseEntity.ok(apiResponse);
    }
}
