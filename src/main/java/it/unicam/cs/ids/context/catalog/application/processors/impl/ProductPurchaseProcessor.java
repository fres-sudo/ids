package it.unicam.cs.ids.context.catalog.application.processors.impl;

import it.unicam.cs.ids.context.catalog.application.mappers.PurchaseMapper;
import it.unicam.cs.ids.context.catalog.application.processors.PurchaseProcessor;
import it.unicam.cs.ids.context.catalog.domain.model.Product;
import it.unicam.cs.ids.context.catalog.domain.model.Purchase;
import it.unicam.cs.ids.context.catalog.domain.repositories.PurchaseRepository;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.PurchaseDTO;
import it.unicam.cs.ids.shared.application.Purchasable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductPurchaseProcessor implements PurchaseProcessor<Product, ProductDTO> {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;

    @Override
    public PurchaseDTO<ProductDTO> process(Product product, Long buyerId, int quantity, Purchase purchase) {
        purchase.setProduct(product);
        product.updateQuantity(quantity);
        return purchaseMapper.toProductDto(purchaseRepository.save(purchase));
    }

    @Override
    public boolean canHandle(Purchasable item) {
        return item instanceof Product;
    }
}