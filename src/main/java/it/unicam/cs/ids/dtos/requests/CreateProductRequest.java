package it.unicam.cs.ids.dtos.requests;

import it.unicam.cs.ids.dtos.DTO;
import it.unicam.cs.ids.enums.Coordinates;
import it.unicam.cs.ids.enums.Currency;
import it.unicam.cs.ids.enums.ProductCategory;
import it.unicam.cs.ids.enums.UnityOfMeasure;

import java.util.Date;
import java.util.List;

public class CreateProductRequest extends DTO {
    private String name;
    private String description;
    private Date productionDate;
    private ProductCategory category;
    private int quantity;
    private double pricePerQuantity;
    private UnityOfMeasure unityOfMeasure;
    private Currency currency;
    private List<String> tags;
    private long producerId;
    private String cultivationMethod;
    private Date expirationDate;
    private Date harvestDate;
    private boolean availableForSale;
    private boolean availableForShipping;
    private int estimatedDeliveryTime;
    private double shippingCost;
    private String returnPolicy;
    private Coordinates productLocation;
    private List<Long> distributorId;
    private List<Long> transformerId;

    public CreateProductRequest(List<Long> transformerId, List<Long> distributorId, Coordinates productLocation, String returnPolicy, double shippingCost, int estimatedDeliveryTime, boolean availableForShipping, boolean availableForSale, Date harvestDate, Date expirationDate, String cultivationMethod, long producerId, List<String> tags, Currency currency, UnityOfMeasure unityOfMeasure, double pricePerQuantity, int quantity, ProductCategory category, Date productionDate, String description, String name) {
        this.transformerId = transformerId;
        this.distributorId = distributorId;
        this.productLocation = productLocation;
        this.returnPolicy = returnPolicy;
        this.shippingCost = shippingCost;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.availableForShipping = availableForShipping;
        this.availableForSale = availableForSale;
        this.harvestDate = harvestDate;
        this.expirationDate = expirationDate;
        this.cultivationMethod = cultivationMethod;
        this.producerId = producerId;
        this.tags = tags;
        this.currency = currency;
        this.unityOfMeasure = unityOfMeasure;
        this.pricePerQuantity = pricePerQuantity;
        this.quantity = quantity;
        this.category = category;
        this.productionDate = productionDate;
        this.description = description;
        this.name = name;
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

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPricePerQuantity() {
        return pricePerQuantity;
    }

    public void setPricePerQuantity(double pricePerQuantity) {
        this.pricePerQuantity = pricePerQuantity;
    }

    public UnityOfMeasure getUnityOfMeasure() {
        return unityOfMeasure;
    }

    public void setUnityOfMeasure(UnityOfMeasure unityOfMeasure) {
        this.unityOfMeasure = unityOfMeasure;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public long getProducerId() {
        return producerId;
    }

    public void setProducerId(long producerId) {
        this.producerId = producerId;
    }

    public String getCultivationMethod() {
        return cultivationMethod;
    }

    public void setCultivationMethod(String cultivationMethod) {
        this.cultivationMethod = cultivationMethod;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(Date harvestDate) {
        this.harvestDate = harvestDate;
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

    public Coordinates getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(Coordinates productLocation) {
        this.productLocation = productLocation;
    }

    public List<Long> getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(List<Long> distributorId) {
        this.distributorId = distributorId;
    }

    public List<Long> getTransformerId() {
        return transformerId;
    }

    public void setTransformerId(List<Long> transformerId) {
        this.transformerId = transformerId;
    }
}
