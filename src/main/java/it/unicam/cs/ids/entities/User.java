package it.unicam.cs.ids.entities;

import java.util.Date;

public class User {
    private long id;
    private String name;
    private String surname;
    private String email;
    private String hashedPassword;
    private String phoneNumber;
    private String address;
    private boolean emailVerified;
    private Date verifiedAt;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
