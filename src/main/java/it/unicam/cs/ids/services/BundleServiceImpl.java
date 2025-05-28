package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.BundleDTO;
import it.unicam.cs.ids.dtos.requests.CreateBundleRequest;

public class BundleServiceImpl implements BundleService {

    private final ApiResponseFactory apiResponseFactory;

    public BundleServiceImpl(ApiResponseFactory apiResponseFactory) {
        this.apiResponseFactory = apiResponseFactory;
    }

    @Override
    public ApiResponse<BundleDTO> createBundle(CreateBundleRequest request) {
        BundleDTO bundle = new BundleDTO();

        return apiResponseFactory.createSuccessResponse(
                "Bundle created successfully",
                bundle
        );
    }
}
