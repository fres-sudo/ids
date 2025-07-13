package it.unicam.cs.ids.context.catalog.infrastructure.web;

import it.unicam.cs.ids.context.catalog.application.services.ProductService;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateProductRequest;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.UpdateProductRequest;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.CreateCertificateRequest;
import it.unicam.cs.ids.context.company.domain.models.CompanyRoles;
import it.unicam.cs.ids.context.identity.infrastructure.security.user.AppUserPrincipal;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
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

    @PostMapping
    @PreAuthorize("hasRole('PRODUCER') or hasRole('DISTRIBUTOR') or hasRole('TRANSFORMER')")
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(
            @RequestBody CreateProductRequest request,
            @AuthenticationPrincipal AppUserPrincipal principal // <-- important
    ) {
        Long companyId = principal.getId();
        request.setCreatorId(companyId);

        String role = principal.getAuthorities().iterator().next().getAuthority();
        CompanyRoles companyRole = CompanyRoles.valueOf(role);
        switch (companyRole) {
            case PRODUCER -> request.setProducerId(companyId);
            case DISTRIBUTOR -> request.setDistributorId(List.of(companyId));
            case TRANSFORMER -> request.setTransformerId(List.of(companyId));
        }

        ApiResponse<ProductDTO> response = responseFactory.createSuccessResponse(
                Messages.Success.PRODUCT_CREATED,
                productService.createProduct(request)
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PatchMapping("/{productId}")
    ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductRequest request) {
        ApiResponse<ProductDTO> response = responseFactory.createSuccessResponse(
                Messages.Success.PRODUCT_CREATED,
                productService.updateProduct(productId, request)
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/certificate", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('PRODUCER') || hasRole('DISTRIBUTOR') || hasRole('TRANSFORMER')")
    ResponseEntity<ApiResponse<ProductDTO>> createCertificate(@RequestPart CreateCertificateRequest request, @RequestPart MultipartFile file) {
        request.setCertificateFile(file);
        ApiResponse<ProductDTO> response = responseFactory.createSuccessResponse(
                Messages.Success.CERTIFICATE_CREATED,
                productService.createCertificate(request)
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
