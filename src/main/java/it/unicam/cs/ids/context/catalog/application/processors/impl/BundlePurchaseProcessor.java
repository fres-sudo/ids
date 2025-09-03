package it.unicam.cs.ids.context.catalog.application.processors.impl;

import it.unicam.cs.ids.context.catalog.application.mappers.PurchaseMapper;
import it.unicam.cs.ids.context.catalog.application.processors.PurchaseProcessor;
import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import it.unicam.cs.ids.context.catalog.domain.model.Purchase;
import it.unicam.cs.ids.context.catalog.domain.repositories.PurchaseRepository;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.PurchaseDTO;
import it.unicam.cs.ids.shared.application.Purchasable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BundlePurchaseProcessor implements PurchaseProcessor<Bundle, BundleDTO> {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;

    @Override
    public PurchaseDTO<BundleDTO> process(Bundle bundle, Long buyerId, int quantity, Purchase purchase) {
        purchase.setBundle(bundle);
        bundle.updateQuantity(quantity);
        return purchaseMapper.toBundleDto(purchaseRepository.save(purchase));
    }

    @Override
    public boolean canHandle(Purchasable item) {
        return item instanceof Bundle;
    }
}