package it.unicam.cs.ids.dtos.filters;

import it.unicam.cs.ids.dtos.DTO;
import it.unicam.cs.ids.enums.SortDirection;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public abstract class FilterParam extends DTO {
    private String page;
    private String pageSize;
    private SortDirection sortDirection;
    private String sortBy;
    private String searchBy;
    private List<String> tags;
    protected double minPrice;
    protected double maxPrice;
}