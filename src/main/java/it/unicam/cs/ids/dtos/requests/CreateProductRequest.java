package it.unicam.cs.ids.dtos.requests;

import it.unicam.cs.ids.dtos.DTO;
import it.unicam.cs.ids.entities.Coordinates;
import it.unicam.cs.ids.enums.Currency;
import it.unicam.cs.ids.enums.ProductCategory;
import it.unicam.cs.ids.enums.UnitOfMeasure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * CreateProductRequest is used to create a new product.
 */
@Data @EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CreateProductRequest extends DTO {
    private String name;
    private String description;
    private Date productionDate;
    private ProductCategory category;
    private int quantity;
    private double pricePerQuantity;
    private UnitOfMeasure unityOfMeasure;
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
}
