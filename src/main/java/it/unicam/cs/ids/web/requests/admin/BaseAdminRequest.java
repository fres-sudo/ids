package it.unicam.cs.ids.web.requests.admin;

import it.unicam.cs.ids.web.requests.user.BaseUserRequest;

/**
 * Represents a base requests for admin-related operations.
 * This class extends BaseUserRequest and can be used as a foundation for specific admin requests.
 */
public abstract class BaseAdminRequest extends BaseUserRequest {
    /**
     * The unique identifier of the entity to be edited.
     */
    protected Long targetEntityId;
}
