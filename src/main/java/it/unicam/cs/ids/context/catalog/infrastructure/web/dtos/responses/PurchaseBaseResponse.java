package it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.responses;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.PurchaseDTO;
import it.unicam.cs.ids.shared.application.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class PurchaseBaseResponse<T extends DTO> extends DTO {
    private PurchaseDTO<T> purchase;
    private String message;
    private boolean success;
}
