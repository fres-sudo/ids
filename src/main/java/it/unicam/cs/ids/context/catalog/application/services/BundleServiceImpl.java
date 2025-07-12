package it.unicam.cs.ids.context.catalog.application.services;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateBundleRequest;

import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import it.unicam.cs.ids.context.catalog.application.mappers.BundleMapper;
import it.unicam.cs.ids.context.catalog.domain.repositories.BundleRepository;

import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.factories.BundleApprovalRequestFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link BundleService},
 * This service handles the creation of bundles.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class BundleServiceImpl implements BundleService {

    private final BundleRepository bundleRepository;
    private final BundleMapper bundleMapper;

    private final BundleApprovalRequestFactory approvalRequestFactory;

    @Override
    public BundleDTO createBundle(CreateBundleRequest request) {
        Bundle bundle = bundleMapper.fromRequest(request);
        Bundle response = bundleRepository.save(bundle);
        approvalRequestFactory.submit(response.getId(), response.getCreator().getId());
        return bundleMapper.toDto(response);
    }
}
