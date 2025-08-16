package it.unicam.cs.ids.web.requests.factories;

public interface ApprovalRequestFactory {
    void submit(Long productId, Long creatorId);
}
