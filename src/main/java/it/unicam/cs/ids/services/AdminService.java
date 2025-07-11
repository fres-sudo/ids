package it.unicam.cs.ids.services;


import it.unicam.cs.ids.dtos.DTO;

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
    <R extends DTO, T> R editEntity(Class<T> entityType, Long id, Object request);

    void deleteEntity(Class<?> entityType, Long id);

    void treatCertificationRequest(Long requestId, boolean verdict);
}
