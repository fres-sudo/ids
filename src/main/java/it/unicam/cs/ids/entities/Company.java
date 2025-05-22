package it.unicam.cs.ids.entities;

import lombok.Data;

import java.util.Date;

@Data
public class Company {
    private long id;
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private String email;
    private String website;
    private String vat;
    private String billingInformation;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
