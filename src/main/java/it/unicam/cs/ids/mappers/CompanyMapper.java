package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.dtos.CompanyDTO;
import it.unicam.cs.ids.dtos.requests.CreateCompanyRequest;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.repositories.CompanyRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public abstract class CompanyMapper {

    @Autowired
    protected CompanyRepository companyRepository;

    public abstract CompanyDTO toDto(Company company);

    @Named("mapCompanyById")
    public Company fromId(Long id) {
        if (id == null) return null;
        return companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company with ID " + id + " not found"));
    }

    @Named("mapCompanyByIdMany")
    public List<Company> fromIdMany(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return null;
        return ids.stream()
                .map(this::fromId)
                .toList();
    }


}
