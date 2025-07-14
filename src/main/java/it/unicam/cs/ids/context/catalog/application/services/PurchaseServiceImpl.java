package it.unicam.cs.ids.context.catalog.application.services;

import it.unicam.cs.ids.context.catalog.application.mappers.PurchaseMapper;
import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import it.unicam.cs.ids.context.catalog.domain.model.Product;
import it.unicam.cs.ids.context.catalog.domain.model.Purchase;
import it.unicam.cs.ids.context.catalog.domain.repositories.BundleRepository;
import it.unicam.cs.ids.context.catalog.domain.repositories.ProductRepository;
import it.unicam.cs.ids.context.catalog.domain.repositories.PurchaseRepository;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.PurchaseDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.PurchaseBundleRequest;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.PurchaseProductRequest;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import it.unicam.cs.ids.shared.application.Finder;
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

    private static final long MILLISECONDS_PER_DAY = 24L * 60 * 60 * 1000;

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final BundleRepository bundleRepository;
    private final UserRepository userRepository;
    private final PurchaseMapper purchaseMapper;

    @Override
    @Transactional
    public PurchaseDTO purchaseProduct(Long productId, Long buyerId, PurchaseProductRequest request) {
        Product product = Finder.findByIdOrThrow(productRepository, productId, "Product not found");

        return processPurchase(product, buyerId, request.getQuantity());
    }

    @Override
    @Transactional
    public PurchaseDTO purchaseBundle(Long bundleId, Long buyerId, PurchaseBundleRequest request) {
        Bundle bundle = Finder.findByIdOrThrow(bundleRepository, bundleId, "Bundle not found");

        return processPurchase(bundle, buyerId, request.getQuantity());
    }

    @Override
    @Transactional
    public Page<PurchaseDTO> getUserPurchases(Long buyerId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Purchase> purchases = purchaseRepository.findAll(paging);
        return purchases.map(purchaseMapper::toDto);
    }

    @Override
    public PurchaseDTO getPurchaseById(Long purchaseId) {
        Purchase purchase = Finder.findByIdOrThrow(purchaseRepository, purchaseId,
                "Purchase not found with id: " + purchaseId);
        
        return purchaseMapper.toDto(purchase);
    }

    private <T extends Purchasable> PurchaseDTO processPurchase(T item, Long buyerId, int quantity) {
        item.validatePurchase(quantity);

        User buyer = Finder.findByIdOrThrow(userRepository, buyerId,
                "User not found with id: " + buyerId);

        Purchase purchase = new Purchase();
        purchase.setName("Purchase#" + Instant.now().getEpochSecond());
        purchase.setBuyer(buyer);
        purchase.setQuantity(quantity);
        purchase.setUnitPrice(item.getUnitPrice());
        purchase.setTotalAmount(item.computeTotalPrice(quantity));
        purchase.setCurrency(item.getCurrency());
        purchase.setShippingCost(item.getShippingCost());
        purchase.setEstimatedDeliveryDate(item.computeDeliveryDate());

        switch (item) {
            case Product product -> purchase.setProduct(product);
            case Bundle bundle -> purchase.setBundle(bundle);
            default -> throw new IllegalArgumentException("Unsupported purchasable type: " + item.getClass());
        }

        item.updateQuantity(quantity);

        return purchaseMapper.toDto(purchaseRepository.save(purchase));
    }
}