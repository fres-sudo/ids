package it.unicam.cs.ids.context.catalog.infrastructure.web.dtos;

import it.unicam.cs.ids.context.catalog.domain.model.PurchaseStatus;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.UserDTO;
import it.unicam.cs.ids.shared.application.DTO;
import it.unicam.cs.ids.shared.application.Purchasable;
import it.unicam.cs.ids.shared.kernel.enums.Currency;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class PurchaseDTO<T> extends DTO implements Serializable {
    private Long id;
    private String name;
    private UserDTO buyer;
    private T item;
    private int quantity;
    private double unitPrice;
    private double totalAmount;
    private Currency currency;
    private Date purchaseDate;
    private PurchaseStatus status;
    private String deliveryAddress;
    private double shippingCost;
    private Date estimatedDeliveryDate;
    private String notes;
    private Date createdAt;
    private Date updatedAt;
}