package com.github.t3chmas.authorizations.core.role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

public interface RolePort {

    /**
     * Find all roles
     *
     * @return A Set of roles, may be empty but never null
     */
    Set<Role> findAll();

    /**
     * Find all roles
     *
     * @param p Subset of roles you're looking for
     * @return A Set of roles, may be empty but never null
     */
    Page<Role> findAll(Pageable p);

    /**
     * Find a permission by its code, without its permissions
     *
     * @param code the code of the role
     * @return The role - if found - without its permissions
     */
    default Optional<Role> findByCode(String code) {
        return findByCode(code, false);
    }

    /**
     * Find a permission by its code
     *
     * @param code               the code of the role
     * @param includePermissions true to get permissions, else false
     * @return The role, if found
     */
    Optional<Role> findByCode(String code, boolean includePermissions);

    /**
     * Store a role. If the object does not exist, create it, else update it.
     *
     * @param role to update. <p /> If a set of permissions is provided, also update list of permissions associated to this role. Use null to avoid this, use Collections.emptySet() to remove all permissions
     * @return the updated role, including its permissions if it was provided in parameters
     */
    Role save(Role role);

    /**
     * Delete the role
     *
     * @param role the role to delete
     */
    void remove(Role role);
}
