package it.unicam.cs.ids.web.requests.company;

import it.unicam.cs.ids.shared.kernel.enums.ProductCategory;
import it.unicam.cs.ids.dto.DTO;
import it.unicam.cs.ids.shared.infrastructure.persistence.Coordinates;
import it.unicam.cs.ids.shared.kernel.enums.Currency;
import it.unicam.cs.ids.shared.kernel.enums.UnitOfMeasure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * BaseProductRequest is an abstract class that serves as a base for product-related requests.
 * It contains common fields that are applicable to all product requests.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseProductRequest extends DTO {
    private String name;
    private String description;
    private Date productionDate;
    private ProductCategory category;
    private int quantity;
    private double pricePerQuantity;
    private UnitOfMeasure unitOfMeasure;
    private Currency currency;
    private List<String> tags;
    private Long producerId;
    private String cultivationMethod;
    private Date expirationDate;
    private Date harvestDate;
    private boolean availableForSale;
    private boolean availableForShipping;
    private int estimatedDeliveryDays;
    private double shippingCost;
    private String returnPolicy;
    private Coordinates productLocation;
    private List<Long> distributorId;
    private List<Long> transformerId;
}