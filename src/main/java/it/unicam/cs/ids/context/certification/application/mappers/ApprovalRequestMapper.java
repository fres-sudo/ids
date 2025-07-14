package it.unicam.cs.ids.context.certification.application.mappers;

import it.unicam.cs.ids.context.catalog.domain.repositories.BundleRepository;
import it.unicam.cs.ids.shared.application.Approvable;
import it.unicam.cs.ids.context.certification.domain.model.ApprovalRequest;
import it.unicam.cs.ids.context.certification.domain.model.RequestEntityType;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.ApprovalRequestDTO;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.SubmitApprovalRequest;
import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import it.unicam.cs.ids.context.catalog.domain.repositories.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
@Component
public abstract class ApprovalRequestMapper {

    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected BundleRepository bundleRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "submittedAt", ignore = true)
    @Mapping(target = "processedAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "requestingCompany", source = "companyId", qualifiedByName = "mapCompanyById")
    public abstract ApprovalRequest fromSubmitRequest(SubmitApprovalRequest dto);

    @Mapping(target = "entity", expression = "java(mapEntityByEntityId(entity.getEntityType(), entity.getEntityId()))")
    public abstract ApprovalRequestDTO<Approvable> toDto(ApprovalRequest entity);

    @Transactional(readOnly = true)
    protected Approvable mapEntityByEntityId(RequestEntityType entityType, Long entityId) {
        return switch (entityType) {
            case PRODUCT -> productRepository.findById(entityId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + entityId));
            case BUNDLE -> bundleRepository.findById(entityId)
                    .orElseThrow(() -> new EntityNotFoundException("Bundle not found with id: " + entityId));
        };
    }
}