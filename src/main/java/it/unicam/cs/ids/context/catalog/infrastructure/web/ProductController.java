package it.unicam.cs.ids.context.catalog.infrastructure.web;

import it.unicam.cs.ids.context.catalog.application.services.ProductService;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateProductRequest;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.UpdateProductRequest;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.CreateCertificateRequest;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.company.domain.models.CompanyRoles;
import it.unicam.cs.ids.context.identity.application.services.AuthService;
import it.unicam.cs.ids.context.identity.infrastructure.security.user.AppUserPrincipal;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;
    private final ApiResponseFactory responseFactory;
    private final AuthService authService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('PRODUCER', 'DISTRIBUTOR', 'TRANSFORMER')")
    public ApiResponse<ProductDTO> createProduct(
            @RequestBody CreateProductRequest request
    ) { //TODO: mov ethe logic in {ProductService}
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

    @PreAuthorize("hasAnyAuthority('PRODUCER', 'DISTRIBUTOR', 'TRANSFORMER')")
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

    @PreAuthorize("hasAnyAuthority('PRODUCER', 'DISTRIBUTOR', 'TRANSFORMER')")
    @DeleteMapping("/{productId}")
    ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(
                responseFactory.createSuccessResponse(Messages.Success.PRODUCT_DELETED, null),
                HttpStatus.OK
        );
    }

    @PostMapping( "/certificate")
    @PreAuthorize("hasAnyAuthority('PRODUCER', 'DISTRIBUTOR', 'TRANSFORMER')")
    ApiResponse<ProductDTO> createCertificate(@Valid @RequestBody CreateCertificateRequest request) {
        return responseFactory.createSuccessResponse(
                Messages.Success.CERTIFICATE_CREATED,
                productService.createCertificate(request)
        );
    }

    @PreAuthorize("hasAnyAuthority('PRODUCER', 'DISTRIBUTOR', 'TRANSFORMER')")
    @PostMapping("/submit/{productId}")
    ApiResponse<ProductDTO> submitProductForApproval(@PathVariable Long productId) {

        AppUserPrincipal company = AppUserPrincipal.fromCompany(authService.getAuthenticatedCompany());
        return responseFactory.createSuccessResponse(
                Messages.Success.PRODUCT_SUBMITTED_FOR_APPROVAL,
                productService.submitProductForApproval(productId, company.getId())
        );
    }
}
