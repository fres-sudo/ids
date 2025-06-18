package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.requests.CreateBundleRequest;

import it.unicam.cs.ids.entities.Bundle;
import it.unicam.cs.ids.mappers.BundleMapper;
import it.unicam.cs.ids.repositories.BundleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link BundleService},
 * This service handles the creation of bundles.
 */
@Service
public class BundleServiceImpl implements BundleService {

    private final BundleRepository bundleRepository;
    private final ApiResponseFactory apiResponseFactory;
    private final BundleMapper bundleMapper;

    @Autowired
    public BundleServiceImpl(ApiResponseFactory apiResponseFactory , BundleRepository bundleRepository, BundleMapper bundleMapper) {
        this.bundleRepository = bundleRepository;
        this.apiResponseFactory = apiResponseFactory;
        this.bundleMapper = bundleMapper;
    }

    @Override
    public ApiResponse<Bundle> createBundle(CreateBundleRequest request) {
        Bundle bundle = bundleMapper.fromRequest(request);
        Bundle createdBundle = bundleRepository.save(bundle);

        return apiResponseFactory.createSuccessResponse(
                "Bundle created successfully",
                createdBundle
        );
    }
}
