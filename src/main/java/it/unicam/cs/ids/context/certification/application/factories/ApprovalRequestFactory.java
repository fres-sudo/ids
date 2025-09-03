package it.unicam.cs.ids.context.certification.application.factories;

public interface ApprovalRequestFactory {
    void submit(Long entityID, Long creatorId);
}
