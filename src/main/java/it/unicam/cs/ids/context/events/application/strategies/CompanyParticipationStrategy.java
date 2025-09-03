package it.unicam.cs.ids.context.events.application.strategies;

import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.events.domain.model.Event;
import org.springframework.stereotype.Component;

@Component
public class CompanyParticipationStrategy implements ParticipationStrategy<Company> {
    
    @Override
    public void validateParticipation(Company company, Event event) {
        if (company.equals(event.getOrganizer())) {
            throw new IllegalArgumentException("Company cannot participate in its own event");
        }
        
        if (!company.canParticipateInEvent(event)) {
            throw new IllegalArgumentException("Company does not meet participation requirements");
        }
        
        if (!event.isRegistrationOpen()) {
            throw new IllegalArgumentException("Registration is closed for this event");
        }
        
        if (!event.hasAvailableSlots()) {
            throw new IllegalArgumentException("No available slots for this event");
        }
    }
    
    @Override
    public boolean canJoinEvent(Company company, Event event) {
        return !company.equals(event.getOrganizer()) 
               && company.canParticipateInEvent(event)
               && event.isRegistrationOpen() 
               && event.hasAvailableSlots();
    }
    
    @Override
    public String generateApplicationMessage(Company company, Event event) {
        return String.format("Company %s requests to participate in event: %s", 
                           company.getName(), event.getName());
    }
}