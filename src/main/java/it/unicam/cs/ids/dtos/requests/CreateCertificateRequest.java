package it.unicam.cs.ids.dtos.requests;

import it.unicam.cs.ids.dtos.DTO;

import java.util.Date;

public class CreateCertificateRequest extends DTO {
    private long productId;
    private String name;
    private long issuerId;
    private String certificateUrl;
    private Date issueDate;
    private Date expirationDate;

    public CreateCertificateRequest(long productId, String name, long issuerId, String certificateUrl, Date issueDate, Date expirationDate) {
        this.productId = productId;
        this.name = name;
        this.issuerId = issuerId;
        this.certificateUrl = certificateUrl;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(long issuerId) {
        this.issuerId = issuerId;
    }

    public String getCertificateUrl() {
        return certificateUrl;
    }

    public void setCertificateUrl(String certificateUrl) {
        this.certificateUrl = certificateUrl;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
