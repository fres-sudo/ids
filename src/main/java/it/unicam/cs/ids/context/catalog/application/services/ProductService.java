package it.unicam.cs.ids.context.catalog.application.services;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductDTO;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.CreateCertificateRequest;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateProductRequest;

/**
 * Product Service defines the operations related to products.
 * @see ProductServiceImpl
 */
public interface ProductService {
    /**
     * Creates a new product based on the provided requests.
     *
     * @param request the requests containing product creation details
     * @return an ApiResponse containing the created ProductDTO
     */
    ProductDTO createProduct(CreateProductRequest request);
    /**
     * Creates a new certificate based on the provided requests.
     *
     * @param request the requests containing certificate creation details
     * @return an ApiResponse containing the created CertificateDTO
     */
    ProductDTO createCertificate(CreateCertificateRequest request);
}
