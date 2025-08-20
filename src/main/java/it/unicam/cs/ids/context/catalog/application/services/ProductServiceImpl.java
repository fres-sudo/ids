package it.unicam.cs.ids.context.catalog.application.services;

import it.unicam.cs.ids.context.catalog.application.mappers.ProductMapper;
import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.catalog.domain.model.Product;
import it.unicam.cs.ids.context.catalog.domain.repositories.ProductRepository;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateProductRequest;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.UpdateProductRequest;
import it.unicam.cs.ids.context.certification.application.mappers.CertificateMapper;
import it.unicam.cs.ids.context.certification.domain.model.ApprovalRequest;
import it.unicam.cs.ids.context.certification.domain.model.Certificate;
import it.unicam.cs.ids.context.certification.domain.model.RequestEntityType;
import it.unicam.cs.ids.context.certification.domain.repositories.ApprovalRequestRepository;
import it.unicam.cs.ids.context.certification.domain.repositories.CertificateRepository;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.factories.ProductApprovalRequestFactory;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.CreateCertificateRequest;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import it.unicam.cs.ids.shared.application.Finder;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.AuthenticationException;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;

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
    private final ApprovalRequestRepository approvalRequestRepository;

    private final ProductApprovalRequestFactory approvalRequestFactory;

    @Override
    public ProductDTO createProduct(@Nonnull CreateProductRequest request) {
        Product product = productMapper.fromRequest(request);
        Company creator = Finder.findByIdOrThrow(companyRepository, request.getCreatorId(),
                "Company with ID " + request.getCreatorId() + " not found.");

        product.setCreator(creator);
        Product response = productRepository.save(product);
        //
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
        //approvalRequestRepository.deleteByEntityTypeAndEntityId(RequestEntityType.PRODUCT, productId);
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

    @Override
    @Transactional
    public ProductDTO submitProductForApproval(Long productId, Long id) {
        Company company = Finder.findByIdOrThrow(companyRepository, id, Messages.Auth.COMPANY_NOT_FOUND);
        Product product = Finder.findByIdOrThrow(productRepository, productId, Messages.Error.PRODUCT_NOT_FOUND);
        if (product.getCreator() == null || !product.getCreator().getId().equals(company.getId())) {
            throw new AuthenticationException(Messages.Auth.INVALID_COMPANY_REQUEST);
        }

        product.setApprovalStatus(ApprovalStatus.PENDING);
        productRepository.save(product);
        approvalRequestFactory.submit(productId, id);
        return productMapper.toDto(product);
    }
}
