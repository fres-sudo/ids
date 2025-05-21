package it.unicam.cs.ids.entities;

import it.unicam.cs.ids.enums.ApprovalStatus;
import it.unicam.cs.ids.enums.Coordinates;
import it.unicam.cs.ids.enums.ProductCategory;
import it.unicam.cs.ids.enums.UnityOfMeasure;

import java.util.Currency;
import java.util.Date;
import java.util.List;

public class Product {

    private long id;
    private String name;
    private String description;
    private Date productionDate;
    private ProductCategory category;
    private ApprovalStatus status;
    private int quantity;
    private double pricePerQuantity;
    private UnityOfMeasure unityOfMeasure;
    private Currency currency;
    private List<String> tags;
    private String producerId;
    private String cultivationMethod;
    private Date expirationDate;
    private Date harvestDate;
    private boolean availableForSale;
    private boolean availableForShipping;
    private int estimatedDeliveryTime;
    private double shippingCost;
    private String returnPolicy;
    private Coordinates productLocation;
    private List<Company> distributors;
    private List<Company> transformers;
    private Company creator;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
