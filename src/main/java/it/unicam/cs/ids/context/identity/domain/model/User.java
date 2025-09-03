package it.unicam.cs.ids.context.identity.domain.model;

import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.events.domain.model.Event;
import it.unicam.cs.ids.shared.application.Participable;
import it.unicam.cs.ids.shared.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * User represents a signed-up user on the platform.
 */
@Entity
@Table(name = "users", schema = "ids_schema")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements Participable {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 255)
    private String surname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(length = 500)
    private String address;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = true; // Initialize to true for testing purposes

    @Column(name = "verified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date verifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private PlatformRoles role = PlatformRoles.BUYER;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    
    @Override
    public void validateParticipation(Event event) {
        if (!emailVerified) {
            throw new IllegalArgumentException("User email must be verified to participate in events");
        }
    }
    
    @Override
    public boolean canParticipateInEvent(Event event) {
        return emailVerified && verifiedAt != null;
    }
    
    @Override
    public String getParticipantIdentifier() {
        return "USER_" + getId();
    }
    
    @Override
    public String getParticipantType() {
        return "USER";
    }
    
    @Override
    public String getContactInfo() {
        return email + (phoneNumber != null ? " | " + phoneNumber : "");
    }
}