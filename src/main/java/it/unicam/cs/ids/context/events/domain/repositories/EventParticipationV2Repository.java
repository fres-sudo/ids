package it.unicam.cs.ids.context.events.domain.repositories;

import it.unicam.cs.ids.context.events.domain.model.EventParticipationV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventParticipationV2Repository extends JpaRepository<EventParticipationV2, Long> {
    
    Optional<EventParticipationV2> findByEventIdAndParticipantIdAndParticipantType(
            Long eventId, Long participantId, String participantType);
    
    List<EventParticipationV2> findByEventId(Long eventId);
    
    List<EventParticipationV2> findByParticipantIdAndParticipantType(Long participantId, String participantType);
    
    @Query("SELECT p FROM EventParticipationV2 p WHERE p.event.organizer.id = :organizerId AND p.status = 'PENDING'")
    List<EventParticipationV2> findPendingParticipationsByOrganizer(@Param("organizerId") Long organizerId);
    
    @Query("SELECT COUNT(p) FROM EventParticipationV2 p WHERE p.event.id = :eventId AND p.status = 'APPROVED'")
    long countApprovedParticipationsByEvent(@Param("eventId") Long eventId);
}