package it.unicam.cs.ids.web.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a response for purchasing a bundle of items.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class PurchaseBundleResponse extends PurchaseBaseResponse {
}
