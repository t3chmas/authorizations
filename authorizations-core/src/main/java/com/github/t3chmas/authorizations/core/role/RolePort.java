package com.github.t3chmas.authorizations.core.role;

import java.util.Optional;
import java.util.Set;

public interface RolePort {

    /**
     * Find all permissions
     *
     * @return A Set of roles, may be empty but never null
     */
    Set<Role> findAll();

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
     */
    void store(Role role);
}
