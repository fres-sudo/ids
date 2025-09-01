package it.unicam.cs.ids.context.events.domain.strategies;

import it.unicam.cs.ids.context.events.domain.model.Event;
import it.unicam.cs.ids.shared.application.Participable;

public interface ParticipationStrategy<T extends Participable> {
    void validateParticipation(T participant, Event event);
    boolean canJoinEvent(T participant, Event event);
    String generateApplicationMessage(T participant, Event event);
}