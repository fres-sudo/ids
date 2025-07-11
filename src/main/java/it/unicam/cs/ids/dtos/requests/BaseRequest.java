package it.unicam.cs.ids.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseRequest implements Serializable {
    /** The unique identifier of the request */
    private Long id;
}
