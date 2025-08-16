package it.unicam.cs.ids.web.requests.certifier;

import it.unicam.cs.ids.model.User;
import it.unicam.cs.ids.shared.infrastructure.persistence.ApprovableEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * CertifierRequest represents a request made by a user to become a certifier.
 * It extends the ApprovableEntity to include approval status and timestamps.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "certifier_requests", schema = "ids_schema")
@Data
@NoArgsConstructor
public class CertifierRequest extends ApprovableEntity {
    @ManyToOne
    private User requestingUser;
}
