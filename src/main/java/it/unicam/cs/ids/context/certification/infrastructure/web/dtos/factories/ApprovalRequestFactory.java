package it.unicam.cs.ids.context.certification.infrastructure.web.dtos.factories;

public interface ApprovalRequestFactory {
    void submit(Long productId, Long creatorId);
}
