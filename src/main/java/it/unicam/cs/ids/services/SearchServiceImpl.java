package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import it.unicam.cs.ids.api.responses.factories.DefaultApiResponseFactory;
import it.unicam.cs.ids.api.responses.models.PaginatedApiResponse;
import it.unicam.cs.ids.dtos.BundleDTO;
import it.unicam.cs.ids.dtos.ProductDTO;
import it.unicam.cs.ids.dtos.filters.BundleFilter;
import it.unicam.cs.ids.dtos.filters.FilterParam;
import it.unicam.cs.ids.dtos.filters.ProductFilter;
import it.unicam.cs.ids.entities.Bundle;
import it.unicam.cs.ids.entities.Product;
import it.unicam.cs.ids.mappers.BundleMapper;
import it.unicam.cs.ids.mappers.ProductMapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchServiceImpl implements SearchService {

    private static final ProductMapper productMapper = new ProductMapper();
    private static final BundleMapper bundleMapper = new BundleMapper();
    private static final ApiResponseFactory apiResponseFactory = new DefaultApiResponseFactory();

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
                "Products retrieved successfully",
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
                "Bundles retrieved successfully",
                filteredBundles,
                Integer.parseInt(filter.getPage()) + 1,
                filteredBundles.size(),
                10
        );
    }
}
