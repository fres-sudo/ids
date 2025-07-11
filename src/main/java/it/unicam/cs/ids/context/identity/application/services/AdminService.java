package it.unicam.cs.ids.context.identity.application.services;


import it.unicam.cs.ids.shared.application.BaseRequest;
import it.unicam.cs.ids.shared.application.DTO;

public interface AdminService {
    /**
     * Edits an entity of type T identified by its ID, using the provided request object.
     *
     * @param entityType the class type of the entity to edit
     * @param id the ID of the entity to edit
     * @param request the request object containing the new data for the entity
     * @param <R> the type of DTO to return
     * @param <T> the type of entity to edit
     * @return a DTO representing the updated entity (R)
     */
    <R extends DTO, T> R editEntity(Class<T> entityType, Long id, BaseRequest request);

    void deleteEntity(Class<?> entityType, Long id);

    // TODO: see if it's gud
    void treatCertificationRequest(Long requestId, boolean verdict);
}
