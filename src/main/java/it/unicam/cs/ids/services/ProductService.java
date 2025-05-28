package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.CertificateDTO;
import it.unicam.cs.ids.dtos.ProductDTO;
import it.unicam.cs.ids.dtos.requests.CreateCertificateRequest;
import it.unicam.cs.ids.dtos.requests.CreateProductRequest;

public interface ProductService {
    ApiResponse<ProductDTO> createProduct(CreateProductRequest request);
    ApiResponse<CertificateDTO> createCertificate(CreateCertificateRequest request);
}
