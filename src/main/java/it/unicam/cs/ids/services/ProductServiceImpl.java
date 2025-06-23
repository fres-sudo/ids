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
import it.unicam.cs.ids.repositories.ProductRepository;
import it.unicam.cs.ids.utils.Messages;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    /** Mapper for converting between Certificate entities and DTOs */
    private static final CertificateMapper certificateMapper = new CertificateMapper();
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    public ApiResponse<Product> createProduct(@Nonnull CreateProductRequest request) {
        Product product = productMapper.fromRequest(request);
        Product response = productRepository.save(product);
        return apiResponseFactory.createSuccessResponse(
                Messages.Success.PRODUCT_CREATED,
                response
        );
    }

    @Override
    public ApiResponse<CertificateDTO> createCertificate(@Nonnull CreateCertificateRequest request) {
        Certificate certificate = new Certificate();
        return apiResponseFactory.createSuccessResponse(
                Messages.Success.CERTIFICATE_CREATED,
                certificateMapper.toDTO(certificate)
        );
    }
}
