package it.unicam.cs.ids.context.catalog.application.services;

import it.unicam.cs.ids.context.catalog.application.factories.PurchaseProcessorFactory;
import it.unicam.cs.ids.context.catalog.application.mappers.PurchaseMapper;
import it.unicam.cs.ids.context.catalog.application.processors.PurchaseProcessor;
import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import it.unicam.cs.ids.context.catalog.domain.model.Product;
import it.unicam.cs.ids.context.catalog.domain.model.Purchase;
import it.unicam.cs.ids.context.catalog.domain.repositories.BundleRepository;
import it.unicam.cs.ids.context.catalog.domain.repositories.ProductRepository;
import it.unicam.cs.ids.context.catalog.domain.repositories.PurchaseRepository;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.ProductDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.PurchaseDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.PurchaseBundleRequest;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.PurchaseProductRequest;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import it.unicam.cs.ids.shared.application.DTO;
import it.unicam.cs.ids.shared.application.Finder;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.application.Purchasable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Implementation of {@link PurchaseService}.
 * This service handles purchase operations for products and bundles.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final ProductRepository productRepository;
    private final BundleRepository bundleRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final PurchaseProcessorFactory processorFactory;
    private final PurchaseMapper purchaseMapper;

    @Override
    @Transactional
    public PurchaseDTO<ProductDTO> purchaseProduct(Long productId, Long buyerId, PurchaseProductRequest request) {
        Product product = Finder.findByIdOrThrow(productRepository, productId, "Product not found");
        return processPurchase(product, buyerId, request.getQuantity());
    }

    @Override
    @Transactional
    public PurchaseDTO<BundleDTO> purchaseBundle(Long bundleId, Long buyerId, PurchaseBundleRequest request) {
        Bundle bundle = Finder.findByIdOrThrow(bundleRepository, bundleId, "Bundle not found");
        return processPurchase(bundle, buyerId, request.getQuantity());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseDTO<?>> getUserPurchases(Long buyerId, Pageable pageable) {
        Page<Purchase> purchases = purchaseRepository.findPurchasesByBuyerId(buyerId, pageable);
        return purchases.map(purchaseMapper::toDto);
    }


    private <T extends Purchasable, R extends DTO> PurchaseDTO<R> processPurchase(T item, Long buyerId, int quantity) {
        item.validatePurchase(quantity);

        User buyer = Finder.findByIdOrThrow(userRepository, buyerId, "User not found with id: " + buyerId);

        Purchase purchase = createBasePurchase(item, buyer, quantity);

        double totalPrice = purchase.getUnitPrice() * quantity;

        if (!buyer.canAfford(totalPrice)) {
            throw new IllegalArgumentException(Messages.Error.INSUFFICIENT_FUNDS);
        }
        buyer.deductBalance(totalPrice);
        userRepository.save(buyer);


        // Delegate to appropriate strategy
        PurchaseProcessor<T, R> processor = processorFactory.getProcessor(item);
        return processor.process(item, buyerId, quantity, purchase);
    }

    private Purchase createBasePurchase(Purchasable item, User buyer, int quantity) {
        Purchase purchase = new Purchase();
        purchase.setName("Purchase#" + Instant.now().getEpochSecond());
        purchase.setBuyer(buyer);
        purchase.setQuantity(quantity);
        purchase.setUnitPrice(item.getUnitPrice());
        purchase.setTotalAmount(item.computeTotalPrice(quantity));
        purchase.setShippingCost(item.getShippingCost());
        purchase.setEstimatedDeliveryDate(item.computeDeliveryDate());
        return purchase;
    }
}