package it.unicam.cs.ids.dtos.requests.company.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents a base request for company-related operations.
 */
@Data
@AllArgsConstructor @NoArgsConstructor
public abstract class BaseCompanyRequest implements Serializable {
    /** The unique identifier of the company */
    private Long id;
}
