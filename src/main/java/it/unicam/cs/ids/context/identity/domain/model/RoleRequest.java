package it.unicam.cs.ids.context.identity.domain.model;

import it.unicam.cs.ids.shared.infrastructure.persistence.ApprovableEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Entity representing a role upgrade request made by a user.
 * This unified entity handles all types of role requests (Certifier, Animator, etc.)
 * following the Single Responsibility and Open/Closed principles.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "role_requests", schema = "ids_schema")
@Data
@NoArgsConstructor
public class RoleRequest extends ApprovableEntity {
    
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "requesting_user_id", nullable = false)
    private User requestingUser;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestType requestType;
    
    /**
     * Creates a new role request for the specified user and request type.
     *
     * @param requestingUser the user making the request
     * @param requestType the type of role being requested
     */
    public RoleRequest(User requestingUser, RequestType requestType) {
        this.requestingUser = requestingUser;
        this.requestType = requestType;
    }
}