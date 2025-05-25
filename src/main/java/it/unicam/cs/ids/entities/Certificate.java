package it.unicam.cs.ids.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Certificate represents a document issued for a product, which is a proof of authenticity or compliance to territorial regulations.
 * All certificates are issued by a registered Certifier (a special user), that is responsible for the validity of the certificate.
 * <b>Every purchasable product</b> (thus platform-visible for signed & unsigned users)
 * <b> should have a certificate</b>, if not it is considered a pending item, waiting for the certifier to issue it.
 *
 */
@Data @EqualsAndHashCode(callSuper = true)
public class Certificate extends BaseEntity{
    private long productId;
    private String description;
    private long issuerId;
    private String certificateUrl;
    private Date issueDate;
    private Date expirationDate;
}