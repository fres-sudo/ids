package it.unicam.cs.ids.context.events.domain.repositories;

import it.unicam.cs.ids.context.events.domain.model.Event;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

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
}
