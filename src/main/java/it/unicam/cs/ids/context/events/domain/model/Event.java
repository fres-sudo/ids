package it.unicam.cs.ids.context.events.domain.model;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.shared.application.Approvable;
import it.unicam.cs.ids.shared.infrastructure.persistence.BaseEntity;
import it.unicam.cs.ids.shared.infrastructure.persistence.Coordinates;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Event represents an organized event on the platform by a company for other companies and buyers.
 * Events require manual confirmation from platform administrators.
 */
@Entity
@Table(name = "events", schema = "ids_schema")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Event extends BaseEntity implements Approvable {

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "registration_deadline")
    private LocalDateTime registrationDeadline;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "participation_fee")
    private Double participationFee;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status = ApprovalStatus.DRAFT;

    @ManyToOne
    @JoinColumn(name = "organizer_user_id", nullable = false)
    private User organizer;

    @Embedded
    private Coordinates eventLocation;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "event_tags", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "event_image_urls", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();

    @Column(name = "requirements", columnDefinition = "TEXT")
    private String requirements;

    @Column(name = "contact_info")
    private String contactInfo;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic = true;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EventParticipation> participations = new ArrayList<>();

    @Override
    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.status = approvalStatus;
    }

    public boolean isRegistrationOpen() {
        LocalDateTime now = LocalDateTime.now();
        return status == ApprovalStatus.APPROVED 
               && (registrationDeadline == null || now.isBefore(registrationDeadline))
               && now.isBefore(startDate);
    }

    public boolean hasAvailableSlots() {
        if (maxParticipants == null) {
            return true;
        }
        long approvedParticipations = participations.stream()
                .filter(p -> p.getStatus() == ApprovalStatus.APPROVED)
                .count();
        return approvedParticipations < maxParticipants;
    }
}
