package it.unicam.cs.ids.dtos;

import java.util.Date;
import java.util.List;

public class CompanyDTO extends DTO {
    private long id;
    private String name;
    private String website;
    private String email;
    private String description;
    private String socialReason;
    private long vat;
    private String address;
    private String phoneNumber;
    private String billingInformation;
    private List<UserDTO> employees;
    private Date createdAt;

    public CompanyDTO(long id, String name, String website, String email, String description, String socialReason, long vat, String address, String phoneNumber, String billingInformation, List<UserDTO> employees, Date createdAt) {
        this.id = id;
        this.name = name;
        this.website = website;
        this.email = email;
        this.description = description;
        this.socialReason = socialReason;
        this.vat = vat;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.billingInformation = billingInformation;
        this.employees = employees;
        this.createdAt = createdAt;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSocialReason() {
        return socialReason;
    }

    public void setSocialReason(String socialReason) {
        this.socialReason = socialReason;
    }

    public long getVat() {
        return vat;
    }

    public void setVat(long vat) {
        this.vat = vat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBillingInformation() {
        return billingInformation;
    }

    public void setBillingInformation(String billingInformation) {
        this.billingInformation = billingInformation;
    }

    public List<UserDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<UserDTO> employees) {
        this.employees = employees;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
