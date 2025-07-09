package it.unicam.cs.ids.dtos;

import it.unicam.cs.ids.entities.Coordinates;
import it.unicam.cs.ids.enums.ApprovalStatus;
import it.unicam.cs.ids.enums.Currency;
import it.unicam.cs.ids.enums.ProductCategory;
import it.unicam.cs.ids.enums.UnitOfMeasure;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ProductDTO extends DTO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Date createdAt;
    private ProductCategory category;
    private ApprovalStatus status;
    private int quantity;
    private double pricePerQuantity;
    private UnitOfMeasure unitOfMeasure;
    private Currency currency;
    private List<String> tags;
    private CompanyDTO company;
    private String cultivationMethod;
    private Date expirationDate;
    private Date harvestDate;
    private boolean availableForShipping;
    private boolean availableForSale;
    private int estimatedDeliveryDays;
    private double shippingCost;
    private String returnPolicy;
    private Coordinates productLocation;
    private List<CompanyDTO> distributors;
    private List<CompanyDTO> transformers;
    private CompanyDTO creator;
    private List<String> imageUrls;
    private List<CertificateDTO> certificates;
}
