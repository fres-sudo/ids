package it.unicam.cs.ids.context.events.application.strategies;

import it.unicam.cs.ids.context.events.domain.model.Event;
import it.unicam.cs.ids.context.identity.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserParticipationStrategy implements ParticipationStrategy<User> {
    
    @Override
    public void validateParticipation(User user, Event event) {
        if (!user.canParticipateInEvent(event)) {
            throw new IllegalArgumentException("User does not meet participation requirements");
        }
        
        if (!event.isRegistrationOpen()) {
            throw new IllegalArgumentException("Registration is closed for this event");
        }
        
        if (!event.hasAvailableSlots()) {
            throw new IllegalArgumentException("No available slots for this event");
        }
        
        if (event.getParticipationFee() != null && event.getParticipationFee() > 0) {
            validatePaymentCapability(user, event);
        }
    }
    
    @Override
    public boolean canJoinEvent(User user, Event event) {
        return user.canParticipateInEvent(event)
               && event.isRegistrationOpen() 
               && event.hasAvailableSlots();
    }
    
    @Override
    public String generateApplicationMessage(User user, Event event) {
        return String.format("User %s %s requests to participate in event: %s", 
                           user.getName(), user.getSurname(), event.getName());
    }
    
    private void validatePaymentCapability(User user, Event event) {
        // Implementation for validating user's payment capability
        // This could integrate with payment service or user wallet
    }
}