package it.unicam.cs.ids.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * User represents a signed-up user on the platform.
 */
@Data @EqualsAndHashCode(callSuper=true)
public class User extends BaseEntity {
    private String surname;
    private String email;
    private String hashedPassword;
    private String phoneNumber;
    private String address;
    private boolean emailVerified;
    private Date verifiedAt;
}
