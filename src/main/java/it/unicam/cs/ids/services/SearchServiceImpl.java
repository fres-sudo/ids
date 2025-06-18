package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import it.unicam.cs.ids.api.responses.factories.DefaultApiResponseFactory;
import it.unicam.cs.ids.api.responses.models.PaginatedApiResponse;
import it.unicam.cs.ids.dtos.BundleDTO;
import it.unicam.cs.ids.dtos.ProductDTO;
import it.unicam.cs.ids.dtos.filters.BundleFilter;
import it.unicam.cs.ids.dtos.filters.FilterParam;
import it.unicam.cs.ids.dtos.filters.ProductFilter;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.mappers.BundleMapper;
import it.unicam.cs.ids.mappers.CompanyMapper;
import it.unicam.cs.ids.mappers.ProductMapper;
import it.unicam.cs.ids.repositories.BundleRepository;
import it.unicam.cs.ids.repositories.CompanyRepository;
import it.unicam.cs.ids.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link SearchService}.
 * This service handles searching for products and bundles based on various filters.
 */
@Service
public class SearchServiceImpl implements SearchService {

    private final ApiResponseFactory apiResponseFactory = new DefaultApiResponseFactory();
    private ProductMapper productMapper;
    private BundleMapper bundleMapper;
    private ProductRepository productRepository;
    private BundleRepository bundleRepository;
    private CompanyMapper companyMapper;
    private CompanyRepository companyRepository;


    //FIXME:: Weak implementation
    @Autowired
    public SearchServiceImpl(ProductMapper productMapper, BundleMapper bundleMapper, ProductRepository productRepository, BundleRepository bundleRepository, CompanyMapper companyMapper, CompanyRepository companyRepository) {
        this.productMapper = productMapper;
        this.bundleMapper = bundleMapper;
        this.productRepository = productRepository;
        this.bundleRepository = bundleRepository;
        this.companyMapper = companyMapper;
        this.companyRepository = companyRepository;
    }
    @Override
    public PaginatedApiResponse<?> search(String type, FilterParam filterParam) {
        List<?> data = switch (type) {
            case "product" -> searchProducts((ProductFilter) filterParam);
            case "bundle" -> searchBundles((BundleFilter) filterParam);
            case "company" -> searchCompanies("" );
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
        if (data == null || data.isEmpty()) {
            return apiResponseFactory.createPaginatedResponse(
                    "No results found for type: " + type,
                    List.of(),
                    0,
                    1, // Assuming page number is 1 for simplicity
                    0 // Assuming page size is 0 for no results
            );
        }
        return apiResponseFactory.createPaginatedResponse(
                "Search results for type: " + type,
                data,
                data.size(),
                1, // Assuming page number is 1 for simplicity
                data.size() // Assuming page size is equal to the number of results for simplicity
        );
    }

    private List<ProductDTO> searchProducts(ProductFilter filter) {
       return null;
    }

    private List<BundleDTO> searchBundles(BundleFilter filter) {
        return null;
    }

    private List<Company> searchCompanies(String name) {
        return companyRepository.findAll();
    }
}
