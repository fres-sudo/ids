package it.unicam.cs.ids.services;

import it.unicam.cs.ids.dto.ProductDTO;
import it.unicam.cs.ids.web.requests.company.UpdateProductRequest;
import it.unicam.cs.ids.web.requests.company.CreateCertificateRequest;
import it.unicam.cs.ids.web.requests.company.CreateProductRequest;
import it.unicam.cs.ids.services.implementation.ProductServiceImpl;

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
     * Updates an existing product based on the provided requests.
     *
     * @param productId the ID of the product to be updated
     * @param request the requests containing product update details
     * @return an ApiResponse containing the updated ProductDTO
     */
    ProductDTO updateProduct(Long productId, UpdateProductRequest request);
    /**
     * Creates a new certificate based on the provided requests.
     *
     * @param request the requests containing certificate creation details
     * @return an ApiResponse containing the created CertificateDTO
     */
    ProductDTO createCertificate(CreateCertificateRequest request);
    /**
     * Deletes a product by its ID.
     *
     * @param productId the ID of the product to be deleted
     */
    void deleteProduct(Long productId);
}
