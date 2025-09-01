package it.unicam.cs.ids.context.events.domain.model;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.shared.application.Approvable;
import it.unicam.cs.ids.shared.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_participations_v2", schema = "ids_schema",
       uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "participant_id", "participant_type"}))
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EventParticipationV2 extends BaseEntity implements Approvable {

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "participant_id", nullable = false)
    private Long participantId;

    @Column(name = "participant_type", nullable = false, length = 50)
    private String participantType;

    @Column(name = "participant_identifier", nullable = false, length = 100)
    private String participantIdentifier;

    @Column(name = "participant_contact_info", length = 500)
    private String participantContactInfo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    @Column(name = "application_message", columnDefinition = "TEXT")
    private String applicationMessage;

    @Column(name = "response_message", columnDefinition = "TEXT")
    private String responseMessage;

    @Column(name = "applied_at", nullable = false)
    private LocalDateTime appliedAt = LocalDateTime.now();

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;

    @Column(name = "special_requirements", columnDefinition = "TEXT")
    private String specialRequirements;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Override
    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.status = approvalStatus;
        if (approvalStatus != ApprovalStatus.PENDING) {
            this.respondedAt = LocalDateTime.now();
        }
    }

    public void approve(String responseMessage) {
        setApprovalStatus(ApprovalStatus.APPROVED);
        this.responseMessage = responseMessage;
    }

    public void reject(String responseMessage) {
        setApprovalStatus(ApprovalStatus.REJECTED);
        this.responseMessage = responseMessage;
    }

    public boolean canBeModified() {
        return status == ApprovalStatus.PENDING || status == ApprovalStatus.DRAFT;
    }
}