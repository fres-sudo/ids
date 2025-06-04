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
import it.unicam.cs.ids.utils.Messages;
import jakarta.annotation.Nonnull;

/**
 * Implementation of {@link ProductService},
 * This service handles the creation of product related entities.
 */
public class ProductServiceImpl implements ProductService {
    /** Factory for creating API responses, used to standardize response creation across the service */
    private static final ApiResponseFactory apiResponseFactory = new DefaultApiResponseFactory();
    /** Mappers for converting between entities and DTOs */
    private static final ProductMapper productMapper = new ProductMapper();
    /** Mapper for converting between Certificate entities and DTOs */
    private static final CertificateMapper certificateMapper = new CertificateMapper();

    @Override
    public ApiResponse<ProductDTO> createProduct(@Nonnull CreateProductRequest request) {
        Product product = new Product();
        return apiResponseFactory.createSuccessResponse(
                Messages.Success.PRODUCT_CREATED,
                productMapper.toDTO(product)
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
