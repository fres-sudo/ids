package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.BundleDTO;
import it.unicam.cs.ids.dtos.requests.CreateBundleRequest;

/**
 * BundleService defines the operations related to bundles.
 * @see BundleServiceImpl
 */
public interface BundleService {
    ApiResponse<BundleDTO> createBundle(CreateBundleRequest request);
}
