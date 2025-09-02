package it.unicam.cs.ids.context.catalog.application.services;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateBundleRequest;

import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import it.unicam.cs.ids.context.catalog.application.mappers.BundleMapper;
import it.unicam.cs.ids.context.catalog.domain.repositories.BundleRepository;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.UpdateBundleRequest;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.factories.BundleApprovalRequestFactory;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import it.unicam.cs.ids.shared.application.Finder;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.AuthenticationException;
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
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BundleServiceImpl implements BundleService {

    private final BundleRepository bundleRepository;
    private final BundleMapper bundleMapper;
    private final CompanyRepository companyRepository;

    private final BundleApprovalRequestFactory approvalRequestFactory;

    @Override
    @Transactional
    public BundleDTO createBundle(@Valid CreateBundleRequest request) {
        Bundle bundle = bundleMapper.fromRequest(request);
        Bundle response = bundleRepository.save(bundle);

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
        return bundleMapper.toDto(response);
    }

    @Override
    public void deleteBundle(Long bundleId, @NotNull Long id) {
        Finder.findByIdOrThrow(bundleRepository, id, "Bundle not found with id: " + id);
        bundleRepository.deleteById(bundleId);
    }

    @Override
    @Transactional
    public BundleDTO submitBundleForApproval(Long bundleId, Long id) {
        Company company = Finder.findByIdOrThrow(companyRepository, id, Messages.Auth.COMPANY_NOT_FOUND);
        Bundle bundle = Finder.findByIdOrThrow(bundleRepository, bundleId, Messages.Error.PRODUCT_NOT_FOUND);
        if (bundle.getDistributor() == null || !bundle.getDistributor().getId().equals(company.getId())) {
            throw new AuthenticationException(Messages.Auth.INVALID_COMPANY_REQUEST);
        }

        bundle.setApprovalStatus(ApprovalStatus.PENDING);
        bundleRepository.save(bundle);
        approvalRequestFactory.submit(bundleId, id);
        return bundleMapper.toDto(bundle);
    }
}
