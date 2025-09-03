package it.unicam.cs.ids.shared.infrastructure.persistence;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public abstract class ApprovableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status = ApprovalStatus.DRAFT;

    @CreationTimestamp
    private LocalDateTime submittedAt;
    @UpdateTimestamp
    private LocalDateTime processedAt;

    @Nullable
    private String comments;
}