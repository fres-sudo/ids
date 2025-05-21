package it.unicam.cs.ids.dtos;

import java.util.Date;

public class CertificateDTO extends DTO {
    private long id;
    private ProductDTO product;
    private CompanyDTO issuer;
    private String name;
    private Date issueDate;
    private Date expirationDate;
    private String certificateUrl;
    private Date createdAt;
}