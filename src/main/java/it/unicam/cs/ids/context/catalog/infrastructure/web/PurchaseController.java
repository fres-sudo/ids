package it.unicam.cs.ids.context.catalog.infrastructure.web;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.PurchaseBundleRequest;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.PurchaseProductRequest;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.responses.PurchaseBundleResponse;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.responses.PurchaseProductResponse;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/users/purchase") // B
public class PurchaseController {

     @PostMapping("/product/{productId}")
     public ResponseEntity<PurchaseProductResponse> purchaseProduct(@RequestBody PurchaseProductRequest request, @PathVariable String productId) {
         return null;
     }

     @PostMapping("/bundle/{bundleId}")
     public ResponseEntity<ApiResponse<PurchaseBundleResponse>> purchaseBundle(@RequestBody PurchaseBundleRequest request, @PathVariable String bundleId) {
         return null;
     }
}
