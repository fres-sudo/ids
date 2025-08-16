    package it.unicam.cs.ids.web.requests.company;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.NoArgsConstructor;

/**
 * CreateProductRequest is used to create a new product.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest extends BaseProductRequest {
    private Long creatorId;
}
