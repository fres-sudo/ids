package it.unicam.cs.ids.context.certification.application.strategies;

import it.unicam.cs.ids.context.catalog.domain.model.Product;
import it.unicam.cs.ids.context.catalog.domain.repositories.ProductRepository;
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
public class ProductApprovalStrategy extends BaseApprovalStrategy<Product> {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    @Override
    public Product findEntity(Long entityId) {
        return Finder.findByIdOrThrow(productRepository, entityId, Messages.Error.PRODUCT_NOT_FOUND);
    }

    @Override
    public void validateOwnership(Product entity, Long requesterId) {
        Company company = Finder.findByIdOrThrow(companyRepository, requesterId, Messages.Auth.COMPANY_NOT_FOUND);
        if (entity.getCreator() == null || !entity.getCreator().getId().equals(company.getId())) {
            throw new AuthenticationException(Messages.Auth.INVALID_COMPANY_REQUEST);
        }
    }

    @Override
    public void performAdditionalValidation(Product entity) {
        if (entity.getQuantity() <= 0) {
            throw new IllegalArgumentException("Product must have positive quantity");
        }
        if (entity.getPricePerQuantity() <= 0) {
            throw new IllegalArgumentException("Product must have valid price");
        }
        if (entity.getName() == null || entity.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product must have a name");
        }
    }

    @Override
    public Product saveEntity(Product entity) {
        return productRepository.save(entity);
    }

    @Override
    public ApprovalRequest createApprovalRequest(Long entityId, Long requesterId) {
        ApprovalRequest request = new ApprovalRequest();
        request.setEntityType(RequestEntityType.PRODUCT);
        request.setEntityId(entityId);
        request.setRequestingCompany(companyRepository.findById(requesterId).orElse(null));
        return request;
    }
}