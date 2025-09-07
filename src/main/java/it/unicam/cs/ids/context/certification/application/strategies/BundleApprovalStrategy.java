package it.unicam.cs.ids.context.certification.application.strategies;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import it.unicam.cs.ids.context.catalog.domain.repositories.BundleRepository;
import it.unicam.cs.ids.context.certification.domain.model.ApprovalRequest;
import it.unicam.cs.ids.context.certification.domain.model.RequestEntityType;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import it.unicam.cs.ids.shared.application.Finder;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BundleApprovalStrategy extends BaseApprovalStrategy<Bundle> {

    private final BundleRepository bundleRepository;
    private final CompanyRepository companyRepository;

    @Override
    public Bundle findEntity(Long entityId) {
        return Finder.findByIdOrThrow(bundleRepository, entityId, Messages.Error.BUNDLE_NOT_FOUND);
    }

    @Override
    public void validateOwnership(Bundle entity, Long requesterId) {
        Company company = Finder.findByIdOrThrow(companyRepository, requesterId, Messages.Auth.COMPANY_NOT_FOUND);
        if (entity.getDistributor() == null || !entity.getDistributor().getId().equals(company.getId())) {
            throw new AuthenticationException(Messages.Auth.INVALID_COMPANY_REQUEST);
        }
    }

    @Override
    public void performAdditionalValidation(Bundle entity) {
        if (entity.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Bundle must contain at least one product");
        }
        if (entity.getQuantity() <= 0) {
            throw new IllegalArgumentException("Bundle must have positive quantity");
        }
        // Validate all products in bundle are approved
        boolean hasUnapprovedProducts = entity.getProducts().stream()
                .anyMatch(bp -> bp.getProduct().getStatus() != ApprovalStatus.APPROVED);
        if (hasUnapprovedProducts) {
            throw new IllegalArgumentException("All products in bundle must be approved");
        }
    }

    @Override
    public Bundle saveEntity(Bundle entity) {
        return bundleRepository.save(entity);
    }

    @Override
    public ApprovalRequest createApprovalRequest(Long entityId, Long requesterId) {
        ApprovalRequest request = new ApprovalRequest();
        request.setEntityType(RequestEntityType.BUNDLE);
        request.setEntityId(entityId);
        request.setRequestingCompany(companyRepository.findById(requesterId).orElse(null));
        return request;
    }
}
