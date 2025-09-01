package it.unicam.cs.ids.shared.application;

import it.unicam.cs.ids.context.events.domain.model.Event;

public interface Participable {
    void validateParticipation(Event event);
    boolean canParticipateInEvent(Event event);
    String getParticipantIdentifier();
    String getParticipantType();
    String getContactInfo();
}