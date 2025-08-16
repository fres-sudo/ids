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
    /**
     * Creates a new bundle based on the provided request.
     *
     * @param request the request containing bundle creation details
     * @return the created BundleDTO
     */
    BundleDTO createBundle(CreateBundleRequest request);

    /**
     * Updates an existing bundle based on the provided request.
     *
     * @param request the request containing bundle update details
     * @return the updated BundleDTO
     */
    BundleDTO updateBundle(UpdateBundleRequest request);

    /**
     * Deletes a bundle by its ID.
     *
     * @param bundleId the ID of the bundle to be deleted
     * @param id the ID of the user requesting the deletion
     */
    void deleteBundle(Long bundleId, @NotNull Long id);
}
