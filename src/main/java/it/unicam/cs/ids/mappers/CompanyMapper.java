package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.dtos.requests.CreateCompanyRequest;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.repositories.CompanyRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class CompanyMapper {

    @Autowired
    protected CompanyRepository companyRepository;

    /**
     * Maps a CreateCompanyRequest to a Company entity.
     *
     * @param request the request containing company data
     * @return the mapped Company entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "employees", ignore = true)
    public abstract Company fromRequest(CreateCompanyRequest request);

    @Named("mapCompanyById")
    public Company fromId(Long id) {
        if (id == null) return null;
        return companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company with ID " + id + " not found"));
    }
}
