package it.unicam.cs.ids.context.catalog.application.mappers;

import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import it.unicam.cs.ids.context.catalog.domain.model.Product;
import it.unicam.cs.ids.context.catalog.domain.model.Purchase;
import it.unicam.cs.ids.context.catalog.domain.model.PurchaseStatus;
import it.unicam.cs.ids.context.catalog.domain.repositories.BundleRepository;
import it.unicam.cs.ids.context.catalog.domain.repositories.ProductRepository;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.PurchaseDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.PurchaseBundleRequest;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.PurchaseProductRequest;
import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import it.unicam.cs.ids.context.identity.application.mappers.UserMapper;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {ProductMapper.class, BundleMapper.class})
@Component
public abstract class PurchaseMapper {

    @Autowired
    protected UserRepository userRepository;
    
    @Autowired
    protected ProductRepository productRepository;
    
    @Autowired
    protected BundleRepository bundleRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "purchaseDate", ignore = true)
    @Mapping(target = "status", expression = "java(it.unicam.cs.ids.context.catalog.domain.model.PurchaseStatus.COMPLETED)")
    @Mapping(target = "name", expression = "java(\"Purchase #\" + java.time.Instant.now().getEpochSecond())")
    @Mapping(target = "buyer", source = "buyerId", qualifiedByName = "mapUserById")
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProductById")
    @Mapping(target = "bundle", ignore = true)
    @Mapping(target = "unitPrice", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "currency", ignore = true)
    @Mapping(target = "shippingCost", ignore = true)
    @Mapping(target = "estimatedDeliveryDate", ignore = true)
    public abstract Purchase fromProductRequest(PurchaseProductRequest request, Long buyerId, Long productId);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "purchaseDate", ignore = true)
    @Mapping(target = "status", expression = "java(it.unicam.cs.ids.context.catalog.domain.model.PurchaseStatus.COMPLETED)")
    @Mapping(target = "name", expression = "java(\"Purchase #\" + java.time.Instant.now().getEpochSecond())")
    @Mapping(target = "buyer", source = "buyerId", qualifiedByName = "mapUserById")
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "bundle", source = "bundleId", qualifiedByName = "mapBundleById")
    @Mapping(target = "unitPrice", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "currency", ignore = true)
    @Mapping(target = "shippingCost", ignore = true)
    @Mapping(target = "estimatedDeliveryDate", ignore = true)
    public abstract Purchase fromBundleRequest(PurchaseBundleRequest request, Long buyerId, Long bundleId);

    public abstract PurchaseDTO toDto(Purchase purchase);

    @Named("mapUserById")
    protected User mapUserById(Long userId) {
        return userId != null ? userRepository.findById(userId).orElse(null) : null;
    }

    @Named("mapProductById")
    protected Product mapProductById(Long productId) {
        return productId != null ? productRepository.findById(productId).orElse(null) : null;
    }

    @Named("mapBundleById")
    protected Bundle mapBundleById(Long bundleId) {
        return bundleId != null ? bundleRepository.findById(bundleId).orElse(null) : null;
    }
}