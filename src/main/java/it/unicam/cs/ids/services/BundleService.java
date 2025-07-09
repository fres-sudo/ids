package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.requests.CreateBundleRequest;
import it.unicam.cs.ids.entities.Bundle;

/**
 * BundleService defines the operations related to bundles.
 * @see BundleServiceImpl
 */
public interface BundleService {
    ApiResponse<Bundle> createBundle(CreateBundleRequest request);
}
