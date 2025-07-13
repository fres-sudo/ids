package it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class PurchaseProductRequest extends PurchaseBaseRequest {
}
