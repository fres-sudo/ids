package it.unicam.cs.ids.dtos.filters;

import it.unicam.cs.ids.enums.ProductCategory;
import lombok.*;

import java.util.List;

@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CompanyFilter extends FilterParam {
    private String companyName; // Added field for company name
    private String companyLocation; // Added field for company location
}