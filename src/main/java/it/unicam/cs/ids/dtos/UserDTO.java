package it.unicam.cs.ids.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class UserDTO extends DTO {
    private long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;
}
