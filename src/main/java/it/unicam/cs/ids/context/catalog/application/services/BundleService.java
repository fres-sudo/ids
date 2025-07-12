package it.unicam.cs.ids.context.catalog.application.services;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateBundleRequest;

/**
 * BundleService defines the operations related to bundles.
 * @see BundleServiceImpl
 */
public interface BundleService {
    BundleDTO createBundle(CreateBundleRequest request);
}
