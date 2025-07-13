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
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Implementation of {@link PurchaseService}.
 * This service handles purchase operations for products and bundles.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final BundleRepository bundleRepository;
    private final UserRepository userRepository;
    private final PurchaseMapper purchaseMapper;

    @Override
    @Transactional
    public PurchaseDTO purchaseProduct(Long productId, Long buyerId, PurchaseProductRequest request) {
        Product product = Finder.findByIdOrThrow(productRepository, productId,
                "Product not found with id: " + productId);
        
        User buyer = Finder.findByIdOrThrow(userRepository, buyerId,
                "User not found with id: " + buyerId);

        validateProductPurchase(product, request.getQuantity());

        Purchase purchase = purchaseMapper.fromProductRequest(request, buyerId, productId);
        purchase.setUnitPrice(product.getPricePerQuantity());
        purchase.setTotalAmount(calculateTotalAmount(product.getPricePerQuantity(), request.getQuantity(), product.getShippingCost()));
        purchase.setCurrency(product.getCurrency());
        purchase.setShippingCost(product.getShippingCost());
        purchase.setEstimatedDeliveryDate(calculateEstimatedDeliveryDate(product.getEstimatedDeliveryDays()));

        updateProductQuantity(product, request.getQuantity());
        
        Purchase savedPurchase = purchaseRepository.save(purchase);
        return purchaseMapper.toDto(savedPurchase);
    }

    @Override
    @Transactional
    public PurchaseDTO purchaseBundle(Long bundleId, Long buyerId, PurchaseBundleRequest request) {
        Bundle bundle = Finder.findByIdOrThrow(bundleRepository, bundleId,
                "Bundle not found with id: " + bundleId);
        
        User buyer = Finder.findByIdOrThrow(userRepository, buyerId,
                "User not found with id: " + buyerId);

        validateBundlePurchase(bundle, request.getQuantity());

        Purchase purchase = purchaseMapper.fromBundleRequest(request, buyerId, bundleId);
        purchase.setUnitPrice(bundle.getPrice());
        purchase.setTotalAmount(calculateTotalAmount(bundle.getPrice(), request.getQuantity(), bundle.getShippingCost()));
        purchase.setCurrency(bundle.getCurrency());
        purchase.setShippingCost(bundle.getShippingCost());
        purchase.setEstimatedDeliveryDate(calculateEstimatedDeliveryDate(bundle.getEstimatedDeliveryDays()));

        updateBundleQuantity(bundle, request.getQuantity());
        
        Purchase savedPurchase = purchaseRepository.save(purchase);
        return purchaseMapper.toDto(savedPurchase);
    }

    @Override
    public List<PurchaseDTO> getUserPurchases(Long buyerId) {
        User buyer = Finder.findByIdOrThrow(userRepository, buyerId,
                "User not found with id: " + buyerId);
        
        List<Purchase> purchases = purchaseRepository.findPurchasesByBuyerId(buyerId);
        return purchases.stream()
                .map(purchaseMapper::toDto)
                .toList();
    }

    @Override
    public PurchaseDTO getPurchaseById(Long purchaseId) {
        Purchase purchase = Finder.findByIdOrThrow(purchaseRepository, purchaseId,
                "Purchase not found with id: " + purchaseId);
        
        return purchaseMapper.toDto(purchase);
    }

    private void validateProductPurchase(Product product, int requestedQuantity) {
        if (!product.isAvailableForSale()) {
            throw new IllegalArgumentException("Product is not available for sale");
        }
        
        if (product.getQuantity() < requestedQuantity) {
            throw new IllegalArgumentException("Insufficient product quantity. Available: " + product.getQuantity() + ", Requested: " + requestedQuantity);
        }
    }

    private void validateBundlePurchase(Bundle bundle, int requestedQuantity) {
        if (!bundle.isAvailableForSale()) {
            throw new IllegalArgumentException("Bundle is not available for sale");
        }
        
        if (bundle.getQuantity() < requestedQuantity) {
            throw new IllegalArgumentException("Insufficient bundle quantity. Available: " + bundle.getQuantity() + ", Requested: " + requestedQuantity);
        }
    }

    private double calculateTotalAmount(double unitPrice, int quantity, double shippingCost) {
        return (unitPrice * quantity) + shippingCost;
    }

    private Date calculateEstimatedDeliveryDate(int estimatedDeliveryDays) {
        if (estimatedDeliveryDays <= 0) {
            return null;
        }
        
        long deliveryTime = System.currentTimeMillis() + (estimatedDeliveryDays * 24L * 60 * 60 * 1000);
        return new Date(deliveryTime);
    }

    private void updateProductQuantity(Product product, int purchasedQuantity) {
        int newQuantity = product.getQuantity() - purchasedQuantity;
        product.setQuantity(newQuantity);
        
        if (newQuantity == 0) {
            product.setAvailableForSale(false);
        }
        
        productRepository.save(product);
    }

    private void updateBundleQuantity(Bundle bundle, int purchasedQuantity) {
        int newQuantity = bundle.getQuantity() - purchasedQuantity;
        bundle.setQuantity(newQuantity);
        
        if (newQuantity == 0) {
            bundle.setAvailableForSale(false);
        }
        
        bundleRepository.save(bundle);
    }
}