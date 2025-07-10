package it.unicam.cs.ids.dtos.requests.admin.actions;


import it.unicam.cs.ids.dtos.requests.admin.BaseAdminRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdminAuthoritativeEditRequest extends BaseAdminRequest {
    /** A map representing the fields and values to update.*/
    private Map<String, Object> payload;

}
