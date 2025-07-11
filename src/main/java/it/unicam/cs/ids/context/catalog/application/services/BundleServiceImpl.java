package it.unicam.cs.ids.context.catalog.application.services;

import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateBundleRequest;

import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import it.unicam.cs.ids.context.catalog.application.mappers.BundleMapper;
import it.unicam.cs.ids.context.catalog.domain.repositories.BundleRepository;
import it.unicam.cs.ids.shared.application.Messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link BundleService},
 * This service handles the creation of bundles.
 */
@Service
public class BundleServiceImpl implements BundleService {

    private final BundleRepository bundleRepository;
    private final BundleMapper bundleMapper;

    @Autowired
    public BundleServiceImpl(BundleRepository bundleRepository, BundleMapper bundleMapper) {
        this.bundleRepository = bundleRepository;
        this.bundleMapper = bundleMapper;
    }

    @Override
    public Bundle createBundle(CreateBundleRequest request) {
        Bundle bundle = bundleMapper.fromRequest(request);
        return bundleRepository.save(bundle);
    }
}
