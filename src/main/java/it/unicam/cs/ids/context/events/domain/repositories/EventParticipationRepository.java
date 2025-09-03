package it.unicam.cs.ids.context.events.domain.repositories;

import it.unicam.cs.ids.context.events.domain.model.EventParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventParticipationRepository extends JpaRepository<EventParticipation, Long> {
    
    Optional<EventParticipation> findByEventIdAndParticipantIdAndParticipantType(
            Long eventId, Long participantId, String participantType);
    
    List<EventParticipation> findByEventId(Long eventId);
    
    List<EventParticipation> findByParticipantIdAndParticipantType(Long participantId, String participantType);
    
    @Query("SELECT p FROM EventParticipation p WHERE p.event.organizer.id = :organizerId AND p.status = 'PENDING'")
    List<EventParticipation> findPendingParticipationsByOrganizer(@Param("organizerId") Long organizerId);
}