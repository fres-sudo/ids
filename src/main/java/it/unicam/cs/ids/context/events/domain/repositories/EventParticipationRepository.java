package it.unicam.cs.ids.context.events.domain.repositories;

import it.unicam.cs.ids.context.events.domain.model.EventParticipation;
import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventParticipationRepository extends JpaRepository<EventParticipation, Long> {
    
    @NotNull
    @EntityGraph(attributePaths = {
            "event",
            "participant"
    })
    Optional<EventParticipation> findById(@NotNull Long id);

    @Query("SELECT ep FROM EventParticipation ep WHERE ep.event.id = :eventId")
    List<EventParticipation> findByEventId(@Param("eventId") Long eventId);

    @Query("SELECT ep FROM EventParticipation ep WHERE ep.participant.id = :participantId")
    List<EventParticipation> findByParticipantId(@Param("participantId") Long participantId);

    @Query("SELECT ep FROM EventParticipation ep WHERE ep.event.id = :eventId AND ep.participant.id = :participantId")
    Optional<EventParticipation> findByEventIdAndParticipantId(@Param("eventId") Long eventId, @Param("participantId") Long participantId);

    @Query("SELECT ep FROM EventParticipation ep WHERE ep.event.id = :eventId AND ep.status = :status")
    List<EventParticipation> findByEventIdAndStatus(@Param("eventId") Long eventId, @Param("status") ApprovalStatus status);

    @Query("SELECT ep FROM EventParticipation ep WHERE ep.participant.id = :participantId AND ep.status = :status")
    List<EventParticipation> findByParticipantIdAndStatus(@Param("participantId") Long participantId, @Param("status") ApprovalStatus status);

    @Query("SELECT ep FROM EventParticipation ep WHERE ep.event.organizer.id = :organizerId AND ep.status = 'PENDING'")
    List<EventParticipation> findPendingParticipationsByOrganizer(@Param("organizerId") Long organizerId);
}
