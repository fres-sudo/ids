package it.unicam.cs.ids.dto;

import it.unicam.cs.ids.shared.kernel.enums.PurchaseStatus;
import it.unicam.cs.ids.shared.kernel.enums.Currency;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class PurchaseDTO extends DTO implements Serializable {
    private Long id;
    private String name;
    private UserDTO buyer;
    private ProductDTO product;
    private BundleDTO bundle;
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