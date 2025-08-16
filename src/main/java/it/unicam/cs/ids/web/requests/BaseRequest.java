package it.unicam.cs.ids.web.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents a base request that can be extended by other request classes.
 * It contains a unique identifier for the request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseRequest implements Serializable {
    /** The unique identifier of the requests */
    private Long id;
}
