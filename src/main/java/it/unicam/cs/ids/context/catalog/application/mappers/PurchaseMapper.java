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
    public abstract PurchaseDTO toDto(Purchase purchase);
}