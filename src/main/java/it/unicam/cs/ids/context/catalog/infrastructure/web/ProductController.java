package it.unicam.cs.ids.context.catalog.infrastructure.web;

import it.unicam.cs.ids.context.catalog.application.services.ProductService;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateProductRequest;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.CreateCertificateRequest;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;
    private final ApiResponseFactory responseFactory;

    @Autowired
    public ProductController(ProductService productService, ApiResponseFactory responseFactory) {
        this.productService = productService;
        this.responseFactory = responseFactory;
    }

    @PostMapping
    ResponseEntity<ApiResponse<ProductDTO>> createProduct(@RequestBody CreateProductRequest request) {
        ApiResponse<ProductDTO> response = responseFactory.createSuccessResponse(
                Messages.Success.PRODUCT_CREATED,
                productService.createProduct(request)
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(path = "/certificate", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<ApiResponse<ProductDTO>> createCertificate(@RequestPart CreateCertificateRequest request, @RequestPart MultipartFile file) {
        request.setCertificateFile(file);
        ApiResponse<ProductDTO> response = responseFactory.createSuccessResponse(
                Messages.Success.CERTIFICATE_CREATED,
                productService.createCertificate(request)
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
