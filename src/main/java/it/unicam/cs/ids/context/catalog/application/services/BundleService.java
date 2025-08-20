package it.unicam.cs.ids.context.catalog.application.services;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateBundleRequest;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.UpdateBundleRequest;

/**
 * BundleService defines the operations related to bundles.
 * @see BundleServiceImpl
 */
public interface BundleService {
    BundleDTO createBundle(CreateBundleRequest request);
    BundleDTO updateBundle(UpdateBundleRequest request);
    void deleteBundle(Long bundleId, Long id);

    BundleDTO submitBundleForApproval(Long bundleId, Long id);
}
