package it.unicam.cs.ids.context.identity.domain.model;

import it.unicam.cs.ids.shared.infrastructure.persistence.ApprovableEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "animator_requests", schema = "ids_schema")
@Data
@NoArgsConstructor
public class AnimatorRequest extends ApprovableEntity {
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User requestingUser;
}