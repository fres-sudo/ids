package it.unicam.cs.ids.dtos.filters;

import it.unicam.cs.ids.enums.SortDirection;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class FilterParam implements Serializable {
    private int pageNo;
    private int pageSize;
    private SortDirection sortDirection;
    private String sortBy;
    private String searchBy;
    private List<String> tags;
}