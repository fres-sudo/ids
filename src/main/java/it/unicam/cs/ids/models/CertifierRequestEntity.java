package it.unicam.cs.ids.models;

import it.unicam.cs.ids.shared.infrastructure.persistence.ApprovableEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * CertifierRequest represents a request made by a user to become a certifier.
 * It extends the ApprovableEntity to include approval status and timestamps.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "certifier_requests", schema = "ids_schema")
@Data
@NoArgsConstructor
public class CertifierRequestEntity extends ApprovableEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_certifier_request_user"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User requestingUser;
}
