package it.unicam.cs.ids.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Company represents a signed-up seller on the platform.
 * Companies can sell single products or bundles of products.
 */
@Data @EqualsAndHashCode(callSuper = true)
public class Company extends BaseEntity {
    private String description;
    private String address;
    private String phoneNumber;
    private String email;
    private String website;
    private String vat;
    private String billingInformation;
}
