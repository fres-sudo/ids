package it.unicam.cs.ids.entities;

import lombok.Data;

import java.util.Date;

@Data
public class Certificate {
    private long id;
    private long productId;
    private String name;
    private String description;
    private long issuerId;
    private String certificateUrl;
    private Date issueDate;
    private Date expirationDate;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}