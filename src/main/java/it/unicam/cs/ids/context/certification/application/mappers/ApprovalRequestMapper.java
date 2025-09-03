package it.unicam.cs.ids.context.certification.application.mappers;

import it.unicam.cs.ids.context.catalog.domain.repositories.BundleRepository;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import it.unicam.cs.ids.context.events.domain.repositories.EventRepository;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import it.unicam.cs.ids.shared.application.Approvable;
import it.unicam.cs.ids.context.certification.domain.model.ApprovalRequest;
import it.unicam.cs.ids.context.certification.domain.model.RequestEntityType;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.ApprovalRequestDTO;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.SubmitApprovalRequest;
import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import it.unicam.cs.ids.context.catalog.domain.repositories.ProductRepository;
import it.unicam.cs.ids.context.identity.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
@Component
public abstract class ApprovalRequestMapper {

    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected BundleRepository bundleRepository;
    @Autowired
    protected EventRepository eventRepository;
    @Autowired
    protected UserRepository userRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "submittedAt", ignore = true)
    @Mapping(target = "processedAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "requestingCompany", source = "companyId", qualifiedByName = "mapCompanyById")
    @Mapping(target = "requestingUser", source = "userId", qualifiedByName = "mapUserById")
    public abstract ApprovalRequest fromSubmitRequest(SubmitApprovalRequest dto);

    @Mapping(target = "entity", expression = "java(mapEntityByEntityId(entity.getEntityType(), entity.getEntityId()))")
    public abstract ApprovalRequestDTO<Approvable> toDto(ApprovalRequest entity);

    @Transactional(readOnly = true)
    protected Approvable mapEntityByEntityId(RequestEntityType entityType, Long entityId) {
        return getApprovable(entityType, entityId, productRepository, bundleRepository, eventRepository);
    }

    public static Approvable getApprovable(RequestEntityType entityType, Long entityId, ProductRepository productRepository, BundleRepository bundleRepository, EventRepository eventRepository) {
        return switch (entityType) {
            case PRODUCT -> productRepository.findById(entityId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + entityId));
            case BUNDLE -> bundleRepository.findById(entityId)
                    .orElseThrow(() -> new EntityNotFoundException("Bundle not found with id: " + entityId));
            case EVENT -> eventRepository.findById(entityId)
                    .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + entityId));
        };
    }

    @Named("mapUserById")
    public User mapUserById(Long id) {
        if (id == null) return null;
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found"));
    }
}