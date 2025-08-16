package it.unicam.cs.ids.services;

import it.unicam.cs.ids.dto.BundleDTO;
import it.unicam.cs.ids.web.requests.company.CreateBundleRequest;
import it.unicam.cs.ids.web.requests.company.UpdateBundleRequest;
import it.unicam.cs.ids.services.implementation.BundleServiceImpl;
import jakarta.validation.constraints.NotNull;

/**
 * BundleService defines the operations related to bundles.
 * @see BundleServiceImpl
 */
public interface BundleService {
    BundleDTO createBundle(CreateBundleRequest request);
    BundleDTO updateBundle(UpdateBundleRequest request);

    void deleteBundle(Long bundleId, @NotNull Long id);
}
