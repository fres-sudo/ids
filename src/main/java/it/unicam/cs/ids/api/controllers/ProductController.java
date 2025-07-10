package it.unicam.cs.ids.api.controllers;

import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.ProductDTO;
import it.unicam.cs.ids.dtos.requests.CreateCertificateRequest;
import it.unicam.cs.ids.dtos.requests.CreateProductRequest;
import it.unicam.cs.ids.services.ProductService;
import jakarta.servlet.annotation.MultipartConfig;
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

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    ResponseEntity<ApiResponse<ProductDTO>> createProduct(@RequestBody CreateProductRequest request) {
        return new ResponseEntity<>(productService.createProduct(request), HttpStatus.CREATED);
    }

    @PostMapping(path = "/certificate", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<ApiResponse<ProductDTO>> createCertificate(@RequestPart CreateCertificateRequest request, @RequestPart MultipartFile file) {
        request.setCertificateFile(file);
        return new ResponseEntity<>(productService.createCertificate(request), HttpStatus.CREATED);
    }
}
