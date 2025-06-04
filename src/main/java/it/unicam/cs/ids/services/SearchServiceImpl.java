package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import it.unicam.cs.ids.api.responses.factories.DefaultApiResponseFactory;
import it.unicam.cs.ids.api.responses.models.PaginatedApiResponse;
import it.unicam.cs.ids.dtos.BundleDTO;
import it.unicam.cs.ids.dtos.DTO;
import it.unicam.cs.ids.dtos.ProductDTO;
import it.unicam.cs.ids.dtos.filters.BundleFilter;
import it.unicam.cs.ids.dtos.filters.FilterParam;
import it.unicam.cs.ids.dtos.filters.ProductFilter;
import it.unicam.cs.ids.entities.Bundle;
import it.unicam.cs.ids.entities.Product;
import it.unicam.cs.ids.mappers.BundleMapper;
import it.unicam.cs.ids.mappers.ProductMapper;
import it.unicam.cs.ids.utils.Messages;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of {@link SearchService}.
 * This service handles searching for products and bundles based on various filters.
 */
public class SearchServiceImpl implements SearchService {
    /** Factory for creating API responses, used to standardize response creation across the service */
    private static final ApiResponseFactory apiResponseFactory = new DefaultApiResponseFactory();
    /** Mapper for converting Between Product entities and DTOs */
    private static final ProductMapper productMapper = new ProductMapper();
    /** Mapper for converting Between Bundle entities and DTOs */
    private static final BundleMapper bundleMapper = new BundleMapper();


    //FIXME:: Weak implementation
    @Override
    public PaginatedApiResponse<?> search(String type, FilterParam filterParam) {
        return switch (type) {
            case "product" -> searchProducts((ProductFilter) filterParam);
            case "bundle" -> searchBundles((BundleFilter) filterParam);
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }

    private PaginatedApiResponse<ProductDTO> searchProducts(ProductFilter filter) {
        List<ProductDTO> filteredProducts = Stream.of(
                        productMapper.toDTO(new Product()),
                        productMapper.toDTO(new Product())
                )
                .filter(product -> filter.getSearchBy() == null || product.getName().contains(filter.getSearchBy()))
                .collect(Collectors.toList());
        return apiResponseFactory.createPaginatedResponse(
                Messages.Success.PRODUCT_RETRIEVED,
                filteredProducts,
                Integer.parseInt(filter.getPage()) + 1,
                filteredProducts.size(),
                10
        );
    }

    private PaginatedApiResponse<BundleDTO> searchBundles(BundleFilter filter) {
        List<BundleDTO> filteredBundles = Stream.of(
                        bundleMapper.toDTO(new Bundle()),
                        bundleMapper.toDTO(new Bundle())
                )
                .filter(bundle -> filter.getSearchBy() == null || bundle.getName().contains(filter.getSearchBy()))
                .collect(Collectors.toList());
        return apiResponseFactory.createPaginatedResponse(
                Messages.Success.BUNDLE_RETRIEVED,
                filteredBundles,
                Integer.parseInt(filter.getPage()) + 1,
                filteredBundles.size(),
                10
        );
    }
}
