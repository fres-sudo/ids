package it.unicam.cs.ids.dtos.requests;

import it.unicam.cs.ids.dtos.DTO;
import it.unicam.cs.ids.enums.Coordinates;
import it.unicam.cs.ids.enums.ProductCategory;

import java.util.List;

public class CreateBundleRequest extends DTO {
    private String name;
    private String description;
    private ProductCategory category;
    private List<CreateBundledProductRequest> bundledProducts;
    private double price;
    private double discountPercentage;
    private List<String> tags;
    private int quantity;
    private String distributorId;
    private boolean availableForSale;
    private boolean availableForShipping;
    private int estimatedDeliveryTime;
    private double shippingCost;
    private String returnPolicy;
    private Coordinates bundleLocation;

    public CreateBundleRequest(
            String name,
            String description,
            ProductCategory category,
            List<CreateBundledProductRequest> bundledProducts,
            double price,
            double discountPercentage,
            List<String> tags,
            int quantity,
            String distributorId,
            boolean availableForSale,
            boolean availableForShipping,
            int estimatedDeliveryTime,
            double shippingCost,
            String returnPolicy,
            Coordinates bundleLocation
    ) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.bundledProducts = bundledProducts;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.tags = tags;
        this.quantity = quantity;
        this.distributorId = distributorId;
        this.availableForSale = availableForSale;
        this.availableForShipping = availableForShipping;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.shippingCost = shippingCost;
        this.returnPolicy = returnPolicy;
        this.bundleLocation = bundleLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public List<CreateBundledProductRequest> getBundledProducts() {
        return bundledProducts;
    }

    public void setBundledProducts(List<CreateBundledProductRequest> bundledProducts) {
        this.bundledProducts = bundledProducts;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public boolean isAvailableForSale() {
        return availableForSale;
    }

    public void setAvailableForSale(boolean availableForSale) {
        this.availableForSale = availableForSale;
    }

    public boolean isAvailableForShipping() {
        return availableForShipping;
    }

    public void setAvailableForShipping(boolean availableForShipping) {
        this.availableForShipping = availableForShipping;
    }

    public int getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(int estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getReturnPolicy() {
        return returnPolicy;
    }

    public void setReturnPolicy(String returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    public Coordinates getBundleLocation() {
        return bundleLocation;
    }

    public void setBundleLocation(Coordinates bundleLocation) {
        this.bundleLocation = bundleLocation;
    }
}
