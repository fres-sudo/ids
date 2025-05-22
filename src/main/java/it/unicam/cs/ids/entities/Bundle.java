package it.unicam.cs.ids.entities;

import it.unicam.cs.ids.enums.ApprovalStatus;
import it.unicam.cs.ids.enums.Coordinates;
import it.unicam.cs.ids.enums.ProductCategory;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Bundle {
    private long id;
    private String name;
    private String description;
    private ProductCategory category;
    private ApprovalStatus status;
    private List<Product> products;
    private double price;
    private double discountPercentage;
    private List<String> tags;
    private int quantity;
    private long distributorId;
    private boolean availableForSale;
    private boolean availableForShipping;
    private int estimatedDeliveryTime;
    private double shippingCost;
    private String returnPolicy;
    private Coordinates bundleLocation;
    private Company creator;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
