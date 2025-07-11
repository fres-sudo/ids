package it.unicam.cs.ids.context.catalog.application.services;

import it.unicam.cs.ids.context.catalog.application.mappers.ProductMapper;
import it.unicam.cs.ids.context.catalog.domain.model.Product;
import it.unicam.cs.ids.context.catalog.domain.repositories.ProductRepository;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateProductRequest;
import it.unicam.cs.ids.context.certification.application.mappers.CertificateMapper;
import it.unicam.cs.ids.context.certification.domain.model.Certificate;
import it.unicam.cs.ids.context.certification.domain.repositories.CertificateRepository;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.CreateCertificateRequest;
import it.unicam.cs.ids.context.storage.application.services.StorageService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;

/**
 * Implementation of {@link ProductService},
 * This service handles the creation of product related entities.
 */
@Service
public class ProductServiceImpl implements ProductService {
    /**
     * Mappers for converting between entities and DTOs
     */
    private final ProductMapper productMapper;
    private final CertificateMapper certificateMapper;
    /**
     * Repository for accessing product data
     */
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
    public ProductDTO createProduct(@Nonnull CreateProductRequest request) {
        Product product = productMapper.fromRequest(request);
        Product response = productRepository.save(product);
        return productMapper.toDto(response);
    }

    @Override
    @Transactional
    public ProductDTO createCertificate(@Nonnull CreateCertificateRequest request) {
        try {
            // Validate product exists first
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product with ID " + request.getProductId() + " not found."));

            // Store the certificate file
            Path path = storageService.store(request.getCertificateFile());

            // Create certificate entity
            Certificate certificate = certificateMapper.fromCreateRequest(request);

            // Generate proper URL for file access
            String fileUrl = "/api/files/" + path.getFileName().toString();
            certificate.setCertificateUrl(fileUrl);

            // Save certificate
            Certificate savedCertificate = certificateRepository.save(certificate);

            // Add certificate to product
            product.getCertificates().add(savedCertificate);
            Product savedProduct = productRepository.save(product);

            return productMapper.toDto(savedProduct);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create certificate: " + e.getMessage(), e);
        }
    }
}
