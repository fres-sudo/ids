package it.unicam.cs.ids.web.requests.user;

import it.unicam.cs.ids.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a base request for purchase-related operations.
 * This class contains common fields that are required for making a purchase.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public abstract class PurchaseBaseRequest extends DTO {
    private int quantity;
    private String deliveryAddress;
    private String notes;
}
