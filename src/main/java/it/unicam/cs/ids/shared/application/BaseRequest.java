package it.unicam.cs.ids.shared.application;

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
