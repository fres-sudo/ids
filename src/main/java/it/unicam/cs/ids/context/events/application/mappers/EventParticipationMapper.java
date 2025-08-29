package it.unicam.cs.ids.context.events.application.mappers;

import it.unicam.cs.ids.context.events.domain.model.EventParticipation;
import it.unicam.cs.ids.context.events.infrastructure.web.dto.EventParticipationDTO;
import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import it.unicam.cs.ids.context.events.domain.repositories.EventRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, 
        unmappedSourcePolicy = ReportingPolicy.IGNORE, 
        uses = {CompanyMapper.class, EventMapper.class})
@Component
public abstract class EventParticipationMapper {

    @Autowired
    protected CompanyRepository companyRepository;
    
    @Autowired
    protected EventRepository eventRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "status", expression = "java(it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus.PENDING)")
    @Mapping(target = "participant", source = "participant.id", qualifiedByName = "mapCompanyById")
    @Mapping(target = "event", source = "event.id", qualifiedByName = "mapEventById")
    @Mapping(target = "appliedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "respondedAt", ignore = true)
    public abstract EventParticipation fromDto(EventParticipationDTO dto);

    @Mapping(target = "canBeModified", expression = "java(participation.canBeModified())")
    public abstract EventParticipationDTO toDto(EventParticipation participation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(new java.util.Date())")
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "participant", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "appliedAt", ignore = true)
    public abstract EventParticipation updateParticipationFromDto(EventParticipationDTO dto, @MappingTarget EventParticipation participation);

    @Named("mapEventById")
    protected it.unicam.cs.ids.context.events.domain.model.Event mapEventById(Long eventId) {
        if (eventId == null) return null;
        return eventRepository.findById(eventId).orElse(null);
    }
}
