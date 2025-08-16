package it.unicam.cs.ids.web.requests.company;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UpdateProductRequest is used to update an existing product.
 * It extends BaseProductRequest to inherit common product attributes.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateProductRequest extends BaseProductRequest {
}
