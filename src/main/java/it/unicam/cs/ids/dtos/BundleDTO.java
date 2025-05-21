package it.unicam.cs.ids.dtos;

import it.unicam.cs.ids.enums.ApprovalStatus;
import it.unicam.cs.ids.enums.Coordinates;
import it.unicam.cs.ids.enums.ProductCategory;

import java.util.List;

public class BundleDTO extends DTO {
    private long id;
    private String name;
    private String description;
    private ProductCategory category;
    private ApprovalStatus status;
    private double price;
    private List<BundledProductDTO> products;
    private double discountPercentage;
    private List<String> tags;
    private int quantity;
    private CompanyDTO distributor;
    private boolean availableForSale;
    private boolean availableForShipping;
    private int estimatedDeliveryTime;
    private double shippingCost;
    private String returnPolicy;
    private Coordinates bundleLocation;
    private CompanyDTO creator;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
}
