package it.unicam.cs.ids.dtos;

import it.unicam.cs.ids.enums.*;

import java.util.Date;
import java.util.List;

public class ProductDTO extends DTO {
    private long id;
    private String name;
    private String description;
    private ApprovalStatus status;
    private Date productionDate;
    private ProductCategory category;
    private int quantity;
    private double pricePerQuantity;
    private UnityOfMeasure unityOfMeasure;
    private Currency currency;
    private List<String> tags;
    private CompanyDTO producer;
    private String cultivationMethod;
    private Date expirationDate;
    private Date harvestDate;
    private boolean availableForSale;
    private boolean availableForShipping;
    private int estimatedDeliveryTime;
    private double shippingCost;
    private String returnPolicy;
    private Coordinates productLocation;
    private List<CompanyDTO> distributors;
    private List<CompanyDTO> transformers;
    private CompanyDTO creator;
    private Date createdAt;
    private Date updatedAt;

    public ProductDTO(long id, String name, String description, ApprovalStatus status, Date productionDate, ProductCategory category, int quantity, double pricePerQuantity, UnityOfMeasure unityOfMeasure, Currency currency, List<String> tags, CompanyDTO producer, String cultivationMethod, Date expirationDate, Date harvestDate, boolean availableForSale, boolean availableForShipping, int estimatedDeliveryTime, double shippingCost, String returnPolicy, Coordinates productLocation, List<CompanyDTO> distributors, List<CompanyDTO> transformers, CompanyDTO creator, Date createdAt, Date updatedAt, Date deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.productionDate = productionDate;
        this.category = category;
        this.quantity = quantity;
        this.pricePerQuantity = pricePerQuantity;
        this.unityOfMeasure = unityOfMeasure;
        this.currency = currency;
        this.tags = tags;
        this.producer = producer;
        this.cultivationMethod = cultivationMethod;
        this.expirationDate = expirationDate;
        this.harvestDate = harvestDate;
        this.availableForSale = availableForSale;
        this.availableForShipping = availableForShipping;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.shippingCost = shippingCost;
        this.returnPolicy = returnPolicy;
        this.productLocation = productLocation;
        this.distributors = distributors;
        this.transformers = transformers;
        this.creator = creator;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public CompanyDTO getCreator() {
        return creator;
    }

    public void setCreator(CompanyDTO creator) {
        this.creator = creator;
    }

    public List<CompanyDTO> getTransformers() {
        return transformers;
    }

    public void setTransformers(List<CompanyDTO> transformers) {
        this.transformers = transformers;
    }

    public List<CompanyDTO> getDistributors() {
        return distributors;
    }

    public void setDistributors(List<CompanyDTO> distributors) {
        this.distributors = distributors;
    }

    public Coordinates getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(Coordinates productLocation) {
        this.productLocation = productLocation;
    }

    public String getReturnPolicy() {
        return returnPolicy;
    }

    public void setReturnPolicy(String returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public int getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(int estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public boolean isAvailableForShipping() {
        return availableForShipping;
    }

    public void setAvailableForShipping(boolean availableForShipping) {
        this.availableForShipping = availableForShipping;
    }

    public boolean isAvailableForSale() {
        return availableForSale;
    }

    public void setAvailableForSale(boolean availableForSale) {
        this.availableForSale = availableForSale;
    }

    public Date getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(Date harvestDate) {
        this.harvestDate = harvestDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCultivationMethod() {
        return cultivationMethod;
    }

    public void setCultivationMethod(String cultivationMethod) {
        this.cultivationMethod = cultivationMethod;
    }

    public CompanyDTO getProducer() {
        return producer;
    }

    public void setProducer(CompanyDTO producer) {
        this.producer = producer;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public UnityOfMeasure getUnityOfMeasure() {
        return unityOfMeasure;
    }

    public void setUnityOfMeasure(UnityOfMeasure unityOfMeasure) {
        this.unityOfMeasure = unityOfMeasure;
    }

    public double getPricePerQuantity() {
        return pricePerQuantity;
    }

    public void setPricePerQuantity(double pricePerQuantity) {
        this.pricePerQuantity = pricePerQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
