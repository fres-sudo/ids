package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.repositories.BundleRepository;
import it.unicam.cs.ids.shared.application.Approvable;
import it.unicam.cs.ids.web.requests.certifier.ApprovalRequest;
import it.unicam.cs.ids.shared.kernel.enums.RequestEntityType;
import it.unicam.cs.ids.dto.ApprovalRequestDTO;
import it.unicam.cs.ids.web.requests.company.SubmitApprovalRequest;
import it.unicam.cs.ids.repositories.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mapper for converting between {@link ApprovalRequest} and {@link ApprovalRequestDTO}.
 * This class handles the mapping of approval requests related to products and bundles.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
@Component
public abstract class ApprovalRequestMapper {
    /** Repository for accessing product data. */
    protected ProductRepository productRepository;
    /** Repository for accessing bundle data. */
    protected BundleRepository bundleRepository;


    /**
     * Maps a {@link SubmitApprovalRequest} to an {@link ApprovalRequest}.
     * This method ignores certain fields that are not applicable during the submission phase,
     * @param dto the DTO containing the details of the approval request to be submitted
     * @return a new {@link ApprovalRequest} instance with the details from the DTO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "submittedAt", ignore = true)
    @Mapping(target = "processedAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "requestingCompany", source = "companyId", qualifiedByName = "mapCompanyById")
    public abstract ApprovalRequest fromSubmitRequest(SubmitApprovalRequest dto);

    /**
     * Maps an {@link ApprovalRequest} to an {@link ApprovalRequestDTO}.
     * This method retrieves the entity associated with the approval request based on its type and ID.
     *
     * @param entity the approval request to be mapped
     * @return a new {@link ApprovalRequestDTO} instance with the details from the approval request
     */
    @Mapping(target = "entity", expression = "java(mapEntityByEntityId(entity.getEntityType(), entity.getEntityId()))")
    public abstract ApprovalRequestDTO<Approvable> toDto(ApprovalRequest entity);

    /**
     * Maps an entity ID to the corresponding {@link Approvable} entity based on the request entity type.
     * This method retrieves either a product or a bundle based on the provided entity type and ID.
     *
     * @param entityType the type of the entity (PRODUCT or BUNDLE)
     * @param entityId   the ID of the entity to be retrieved
     * @return the corresponding {@link Approvable} entity
     * @throws EntityNotFoundException if no entity is found with the given ID
     */
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