package it.unicam.cs.ids.dtos;

import it.unicam.cs.ids.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
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
    private List<CertificateDTO> certificates;
    private List<String> imageUrls;
    private Date createdAt;
    private Date updatedAt;
}
