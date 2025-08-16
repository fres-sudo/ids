package it.unicam.cs.ids.web.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseRequest implements Serializable {
    /** The unique identifier of the requests */
    private Long id;
}
