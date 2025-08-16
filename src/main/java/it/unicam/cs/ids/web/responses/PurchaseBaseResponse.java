package it.unicam.cs.ids.web.responses;

import it.unicam.cs.ids.dto.PurchaseDTO;
import it.unicam.cs.ids.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public abstract class PurchaseBaseResponse extends DTO {
    private PurchaseDTO purchase;
    private String message;
    private boolean success;
}
