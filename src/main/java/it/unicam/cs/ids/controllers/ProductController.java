package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.services.ProductService;
import it.unicam.cs.ids.dto.ProductDTO;
import it.unicam.cs.ids.shared.kernel.enums.PlatformRoles;
import it.unicam.cs.ids.web.requests.company.CreateProductRequest;
import it.unicam.cs.ids.web.requests.company.UpdateProductRequest;
import it.unicam.cs.ids.web.requests.company.CreateCertificateRequest;
import it.unicam.cs.ids.models.Company;
import it.unicam.cs.ids.shared.kernel.enums.CompanyRoles;
import it.unicam.cs.ids.services.AuthService;
import it.unicam.cs.ids.security.user.AppUserPrincipal;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.infrastructure.api.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing products.
 * Provides endpoints for creating, updating, and certifying products.
 * Requires the user to have the {@link PlatformRoles#COMPANY role to access these endpoints.
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("products")
public class ProductController {
    /** Service for handling product-related operations */
    private final ProductService productService;
    /** Service for handling authentication and authorization */
    private final AuthService authService;
    /** Factory for creating API responses */
    private final ApiResponseFactory responseFactory;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('PRODUCER', 'DISTRIBUTOR', 'TRANSFORMER', 'ADMIN')")
    public ApiResponse<ProductDTO> createProduct(
            @RequestBody CreateProductRequest request
    ) {
        Company company = authService.getAuthenticatedCompany();
        System.out.println("ROLE: " + company.getRole());
        Long companyId = company.getId();
        request.setCreatorId(companyId);
        CompanyRoles role = company.getRole();
        switch (role) {
            case PRODUCER -> request.setProducerId(companyId);
            case DISTRIBUTOR -> request.setDistributorId(List.of(companyId));
            case TRANSFORMER -> request.setTransformerId(List.of(companyId));
        }

        return responseFactory.createSuccessResponse(
                Messages.Success.PRODUCT_CREATED,
                productService.createProduct(request)
        );
    }

    @PreAuthorize("hasAnyAuthority('PRODUCER', 'DISTRIBUTOR', 'TRANSFORMER', 'ADMIN')")
    @PatchMapping("/{productId}")
    ApiResponse<ProductDTO> updateProduct(
            @PathVariable Long productId,
            @RequestBody UpdateProductRequest request
    ) {
        return responseFactory.createSuccessResponse(
                Messages.Success.PRODUCT_CREATED,
                productService.updateProduct(productId, request)
        );
    }

    @PostMapping( "/certificate")
    @PreAuthorize("hasAnyAuthority('PRODUCER', 'DISTRIBUTOR', 'TRANSFORMER', 'ADMIN')")
    ApiResponse<ProductDTO> createCertificate(@Valid @RequestBody CreateCertificateRequest request) {
        return responseFactory.createSuccessResponse(
                Messages.Success.CERTIFICATE_CREATED,
                productService.createCertificate(request)
        );
    }
}
