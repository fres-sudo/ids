package it.unicam.cs.ids.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseRequest {
    /** The unique identifier of the request */
    private Long id;
}
