package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.BundleDTO;
import it.unicam.cs.ids.dtos.requests.CreateBundleRequest;
import it.unicam.cs.ids.utils.Messages;
import lombok.RequiredArgsConstructor;

/**
 * Implementation of {@link BundleService},
 * This service handles the creation of bundles.
 */
@RequiredArgsConstructor
public class BundleServiceImpl implements BundleService {
    /** Factory for creating API responses, used to standardize response creation across the service */
    private final ApiResponseFactory apiResponseFactory;

    @Override
    public ApiResponse<BundleDTO> createBundle(CreateBundleRequest request) {
        BundleDTO bundle = new BundleDTO();
        return apiResponseFactory.createSuccessResponse(
                Messages.Success.BUNDLE_CREATED,
                bundle
        );
    }
}
