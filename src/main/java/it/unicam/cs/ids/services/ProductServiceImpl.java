package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import it.unicam.cs.ids.api.responses.factories.DefaultApiResponseFactory;
import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.CertificateDTO;
import it.unicam.cs.ids.dtos.ProductDTO;
import it.unicam.cs.ids.dtos.requests.CreateCertificateRequest;
import it.unicam.cs.ids.dtos.requests.CreateProductRequest;
import it.unicam.cs.ids.entities.Certificate;
import it.unicam.cs.ids.entities.Product;
import it.unicam.cs.ids.mappers.CertificateMapper;
import it.unicam.cs.ids.mappers.ProductMapper;
import it.unicam.cs.ids.repositories.CertificateRepository;
import it.unicam.cs.ids.repositories.ProductRepository;
import it.unicam.cs.ids.utils.Messages;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.List;

/**
 * Implementation of {@link ProductService},
 * This service handles the creation of product related entities.
 */
@Service
public class ProductServiceImpl implements ProductService {
    /** Factory for creating API responses, used to standardize response creation across the service */
    private static final ApiResponseFactory apiResponseFactory = new DefaultApiResponseFactory();
    /** Mappers for converting between entities and DTOs */
    private final ProductMapper productMapper;
    private final CertificateMapper certificateMapper;
    /** Repository for accessing product data */
    private final CertificateRepository certificateRepository;
    private final ProductRepository productRepository;

    private final StorageService storageService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CertificateRepository certificateRepository, StorageService storageService, CertificateMapper certificateMapper) {
        this.productMapper = productMapper;
        this.certificateRepository = certificateRepository;
        this.productRepository = productRepository;
        this.storageService = storageService;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public ApiResponse<ProductDTO> createProduct(@Nonnull CreateProductRequest request) {
        Product product = productMapper.fromRequest(request);
        Product response = productRepository.save(product);
        return apiResponseFactory.createSuccessResponse(
                Messages.Success.PRODUCT_CREATED,
                productMapper.toDto(response)
        );
    }

    @Override
    public ApiResponse<ProductDTO> createCertificate(@Nonnull CreateCertificateRequest request) {
       Path path = storageService.store(request.getCertificateFile());
       Certificate certificate = certificateMapper.fromCreateRequest(request);
       certificate.setCertificateUrl(path.getFileName().toString());
       certificateRepository.save(certificate);
       Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new IllegalArgumentException("Product with ID " + request.getProductId() + " not found."));
       return apiResponseFactory.createSuccessResponse(
                Messages.Success.CERTIFICATE_CREATED,
                productMapper.toDto(product)
        );
    }
}
