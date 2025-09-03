package it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.responses;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class PurchaseBundleResponse extends PurchaseBaseResponse<BundleDTO> {
}
