package it.unicam.cs.ids.context.events.application.factories;

import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.events.application.strategies.CompanyParticipationStrategy;
import it.unicam.cs.ids.context.events.application.strategies.ParticipationStrategy;
import it.unicam.cs.ids.context.events.application.strategies.UserParticipationStrategy;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.shared.application.Participable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParticipationStrategyFactory {
    
    private final CompanyParticipationStrategy companyStrategy;
    private final UserParticipationStrategy userStrategy;
    
    @SuppressWarnings("unchecked")
    public <T extends Participable> ParticipationStrategy<T> getStrategy(T participant) {
        return switch (participant) {
            case Company company -> (ParticipationStrategy<T>) companyStrategy;
            case User user -> (ParticipationStrategy<T>) userStrategy;
            default -> throw new IllegalArgumentException("Unsupported participant type: " + participant.getClass());
        };
    }
}