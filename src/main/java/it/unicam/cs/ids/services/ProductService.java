package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.CertificateDTO;
import it.unicam.cs.ids.dtos.ProductDTO;
import it.unicam.cs.ids.dtos.requests.CreateCertificateRequest;
import it.unicam.cs.ids.dtos.requests.CreateProductRequest;
import it.unicam.cs.ids.entities.Certificate;
import it.unicam.cs.ids.entities.Product;

import java.util.List;

/**
 * Product Service defines the operations related to products.
 * @see ProductServiceImpl
 */
public interface ProductService {
    /**
     * Creates a new product based on the provided request.
     *
     * @param request the request containing product creation details
     * @return an ApiResponse containing the created ProductDTO
     */
    ApiResponse<ProductDTO> createProduct(CreateProductRequest request);
    /**
     * Creates a new certificate based on the provided request.
     *
     * @param request the request containing certificate creation details
     * @return an ApiResponse containing the created CertificateDTO
     */
    ApiResponse<ProductDTO> createCertificate(CreateCertificateRequest request);
}
