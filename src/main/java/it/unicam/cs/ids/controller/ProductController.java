package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.services.ProductService;
import it.unicam.cs.ids.dto.ProductDTO;
import it.unicam.cs.ids.web.requests.company.CreateProductRequest;
import it.unicam.cs.ids.web.requests.company.UpdateProductRequest;
import it.unicam.cs.ids.web.requests.company.CreateCertificateRequest;
import it.unicam.cs.ids.model.Company;
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

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;
    private final ApiResponseFactory responseFactory;
    private final AuthService authService;

    @PostMapping
    @PreAuthorize("hasRole('PRODUCER') or hasRole('DISTRIBUTOR') or hasRole('TRANSFORMER')")
    public ApiResponse<ProductDTO> createProduct(
            @RequestBody CreateProductRequest request,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) {
        Company company = authService.getAuthenticatedCompany();
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

    @PreAuthorize("hasRole('PRODUCER') or hasRole('DISTRIBUTOR') or hasRole('TRANSFORMER')")
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
    @PreAuthorize("hasRole('PRODUCER') || hasRole('DISTRIBUTOR') || hasRole('TRANSFORMER')")
    ApiResponse<ProductDTO> createCertificate(@Valid @RequestBody CreateCertificateRequest request) {
        return responseFactory.createSuccessResponse(
                Messages.Success.CERTIFICATE_CREATED,
                productService.createCertificate(request)
        );
    }
}
