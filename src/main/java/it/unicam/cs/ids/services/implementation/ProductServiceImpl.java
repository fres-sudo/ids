package it.unicam.cs.ids.services.implementation;

import it.unicam.cs.ids.mappers.ProductMapper;
import it.unicam.cs.ids.models.Product;
import it.unicam.cs.ids.repositories.ProductRepository;
import it.unicam.cs.ids.dto.ProductDTO;
import it.unicam.cs.ids.web.requests.company.CreateProductRequest;
import it.unicam.cs.ids.web.requests.company.UpdateProductRequest;
import it.unicam.cs.ids.mappers.CertificateMapper;
import it.unicam.cs.ids.models.Certificate;
import it.unicam.cs.ids.repositories.CertificateRepository;
import it.unicam.cs.ids.web.requests.factories.ProductApprovalRequestFactory;
import it.unicam.cs.ids.web.requests.company.CreateCertificateRequest;
import it.unicam.cs.ids.models.Company;
import it.unicam.cs.ids.repositories.CompanyRepository;
import it.unicam.cs.ids.services.ProductService;
import it.unicam.cs.ids.shared.application.Finder;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link ProductService},
 * This service handles the creation of product related entities.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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
    private final CompanyRepository companyRepository;

    private final ProductApprovalRequestFactory approvalRequestFactory;

    @Override
    public ProductDTO createProduct(@Nonnull CreateProductRequest request) {
        Product product = productMapper.fromRequest(request);
        Company creator = Finder.findByIdOrThrow(companyRepository, request.getCreatorId(),
                "Company with ID " + request.getCreatorId() + " not found.");

        product.setCreator(creator);
        Product response = productRepository.save(product);
        approvalRequestFactory.submit(response.getId(), response.getCreator().getId());
        return productMapper.toDto(response);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long productId, UpdateProductRequest request) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Product product = productMapper.updateProductFromRequest(request, existingProduct);
        Product updatedProduct = productRepository.save(product);
        // Submit approval request for the updated product
        approvalRequestFactory.submit(updatedProduct.getId(), updatedProduct.getCreator().getId());
        return productMapper.toDto(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = Finder.findByIdOrThrow(productRepository, productId, "Product not found");
        productRepository.delete(product);
    }

    @Override
    @Transactional
    public ProductDTO createCertificate(@Nonnull CreateCertificateRequest request) {
        try {
            // Validate product exists first
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product with ID " + request.getProductId() + " not found."));
            // Create certificate entity
            Certificate certificate = certificateMapper.fromCreateRequest(request);
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
