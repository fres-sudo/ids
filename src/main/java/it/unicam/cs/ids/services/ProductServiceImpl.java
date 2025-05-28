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

public class ProductServiceImpl implements ProductService {

    private static final ApiResponseFactory apiResponseFactory = new DefaultApiResponseFactory();
    private static final ProductMapper productMapper = new ProductMapper();
    private static final CertificateMapper certificateMapper = new CertificateMapper();

    @Override
    public ApiResponse<ProductDTO> createProduct(CreateProductRequest request) {
        Product product = new Product();
        return apiResponseFactory.createSuccessResponse(
                "Product created successfully",
                productMapper.toDTO(product)
        );
    }

    @Override
    public ApiResponse<CertificateDTO> createCertificate(CreateCertificateRequest request) {
        Certificate certificate = new Certificate();
        return apiResponseFactory.createSuccessResponse(
                "Certificate created successfully",
                certificateMapper.toDTO(certificate)
        );
    }
}
