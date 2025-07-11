package it.unicam.cs.ids.context.catalog.application.services;

import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateBundleRequest;
import it.unicam.cs.ids.context.catalog.domain.model.Bundle;

/**
 * BundleService defines the operations related to bundles.
 * @see BundleServiceImpl
 */
public interface BundleService {
    Bundle createBundle(CreateBundleRequest request);
}
