package it.unicam.cs.ids.context.events.application.mappers;

import it.unicam.cs.ids.context.events.domain.model.Event;
import it.unicam.cs.ids.context.events.infrastructure.web.dto.EventDTO;
import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, 
        unmappedSourcePolicy = ReportingPolicy.IGNORE, 
        uses = {CompanyMapper.class, EventParticipationMapper.class})
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public abstract class EventMapper {

    protected CompanyRepository companyRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "status", expression = "java(it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus.DRAFT)")
    @Mapping(target = "organizer", source = "organizer.id", qualifiedByName = "mapCompanyById")
    @Mapping(target = "participations", ignore = true)
    public abstract Event fromDto(EventDTO dto);

    @Mapping(target = "registrationOpen", expression = "java(event.isRegistrationOpen())")
    @Mapping(target = "hasAvailableSlots", expression = "java(event.hasAvailableSlots())")
    public abstract EventDTO toDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(new java.util.Date())")
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "organizer", ignore = true)
    @Mapping(target = "participations", ignore = true)
    public abstract Event updateEventFromDto(EventDTO dto, @MappingTarget Event event);
}
