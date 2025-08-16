package it.unicam.cs.ids.web.requests.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a request to purchase a bundle of items.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class PurchaseBundleRequest extends PurchaseBaseRequest {
}
