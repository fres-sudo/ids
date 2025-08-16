package it.unicam.cs.ids.services.implementation;

import it.unicam.cs.ids.dto.BundleDTO;
import it.unicam.cs.ids.web.requests.company.CreateBundleRequest;

import it.unicam.cs.ids.models.Bundle;
import it.unicam.cs.ids.mappers.BundleMapper;
import it.unicam.cs.ids.repositories.BundleRepository;

import it.unicam.cs.ids.web.requests.company.UpdateBundleRequest;
import it.unicam.cs.ids.web.requests.factories.BundleApprovalRequestFactory;
import it.unicam.cs.ids.models.Company;
import it.unicam.cs.ids.services.AuthService;
import it.unicam.cs.ids.services.BundleService;
import it.unicam.cs.ids.shared.application.Finder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link BundleService},
 * This service handles the creation of bundles.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class BundleServiceImpl implements BundleService {

    private final BundleRepository bundleRepository;
    private final BundleMapper bundleMapper;

    private final AuthService authService;

    private final BundleApprovalRequestFactory approvalRequestFactory;

    @Override
    @Transactional
    public BundleDTO createBundle(@Valid CreateBundleRequest request) {
        Company creator = authService.getAuthenticatedCompany();
        request.setDistributorId(creator.getId());
        Bundle bundle = bundleMapper.fromRequest(request);

        Bundle response = bundleRepository.save(bundle);
        approvalRequestFactory.submit(response.getId(), response.getDistributor().getId());
        return bundleMapper.toDto(response);
    }

    @Override
    @Transactional
    public BundleDTO updateBundle(@Valid UpdateBundleRequest request) {
        Bundle bundle = Finder.findByIdOrThrow(
                bundleRepository,
                request.getId(),
                "Bundle not found with id: " + request.getId()
        );
        Bundle updatedBundle = bundleMapper.updateFromRequest(bundle, request);
        Bundle response = bundleRepository.save(updatedBundle);
        approvalRequestFactory.submit(response.getId(), response.getDistributor().getId());
        return bundleMapper.toDto(response);
    }

    @Override
    public void deleteBundle(Long bundleId, @NotNull Long id) {
        bundleRepository.deleteById(bundleId);
    }
}
