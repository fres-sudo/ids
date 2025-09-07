package it.unicam.cs.ids.context.certification.application.strategies;

import it.unicam.cs.ids.context.certification.domain.model.ApprovalRequest;
import it.unicam.cs.ids.context.certification.domain.model.RequestEntityType;
import it.unicam.cs.ids.context.events.domain.model.Event;
import it.unicam.cs.ids.context.events.domain.repositories.EventRepository;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import it.unicam.cs.ids.shared.application.Finder;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EventApprovalStrategy extends BaseApprovalStrategy<Event> {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public Event findEntity(Long entityId) {
        return Finder.findByIdOrThrow(eventRepository, entityId, Messages.Error.EVENT_NOT_FOUND);
    }

    @Override
    public void validateOwnership(Event entity, Long requesterId) {
        User user = Finder.findByIdOrThrow(userRepository, requesterId, Messages.Auth.USER_NOT_FOUND);
        if (entity.getOrganizer() == null || !entity.getOrganizer().getId().equals(user.getId())) {
            throw new AuthenticationException(Messages.Auth.INVALID_USER_REQUEST);
        }
    }

    @Override
    public void performAdditionalValidation(Event entity) {
        LocalDateTime now = LocalDateTime.now();
        if (entity.getStartDate().isBefore(now)) {
            throw new IllegalArgumentException("Event start date must be in the future");
        }
        if (entity.getEndDate().isBefore(entity.getStartDate())) {
            throw new IllegalArgumentException("Event end date must be after start date");
        }
        if (entity.getRegistrationDeadline() != null &&
                entity.getRegistrationDeadline().isAfter(entity.getStartDate())) {
            throw new IllegalArgumentException("Registration deadline must be before event start");
        }
    }

    @Override
    public void saveEntity(Event entity) {
         eventRepository.save(entity);
    }

    @Override
    public ApprovalRequest createApprovalRequest(Long entityId, Long requesterId) {
        ApprovalRequest request = new ApprovalRequest();
        request.setEntityType(RequestEntityType.EVENT);
        request.setEntityId(entityId);
        request.setRequestingUser(userRepository.findById(requesterId).orElse(null));
        return request;
    }
}