package it.unicam.cs.ids.context.events.domain.repositories;

import it.unicam.cs.ids.context.events.domain.model.Event;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    
    @NotNull
    @EntityGraph(attributePaths = {
            "organizer",
            "participations",
            "tags",
            "imageUrls"
    })
    Optional<Event> findById(@NotNull Long id);

    @Query("SELECT e FROM Event e WHERE e.organizer.id = :organizerId")
    List<Event> findByOrganizerId(@Param("organizerId") Long organizerId);

    @Query("SELECT e FROM Event e WHERE e.isPublic = true AND e.status = 'APPROVED'")
    List<Event> findPublicApprovedEvents();

    @Query("SELECT e FROM Event e WHERE e.startDate > :now AND e.status = 'APPROVED'")
    List<Event> findUpcomingEvents(@Param("now") LocalDateTime now);

    @Query("SELECT e FROM Event e WHERE e.registrationDeadline > :now AND e.status = 'APPROVED'")
    List<Event> findEventsWithOpenRegistration(@Param("now") LocalDateTime now);
}
