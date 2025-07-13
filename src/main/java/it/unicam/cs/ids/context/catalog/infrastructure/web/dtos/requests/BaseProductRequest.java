package it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests;

import it.unicam.cs.ids.context.catalog.domain.model.ProductCategory;
import it.unicam.cs.ids.shared.application.DTO;
import it.unicam.cs.ids.shared.infrastructure.persistence.Coordinates;
import it.unicam.cs.ids.shared.kernel.enums.Currency;
import it.unicam.cs.ids.shared.kernel.enums.UnitOfMeasure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
    private UnitOfMeasure unityOfMeasure;
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