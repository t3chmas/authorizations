package com.github.t3chmas.authorizations.core.permission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

public interface PermissionPort {

    /**
     * Find all permissions
     *
     * @return A Set of permissions, may be empty but never null
     */
    Set<Permission> findAll();

    /**
     * Find all permissions
     *
     * @param p Subset of permissions you're looking for
     * @return A Set of permissions, may be empty but never null
     */
    Page<Permission> findAll(Pageable p);

    /**
     * Find a permission by its code
     *
     * @return The permission, if found
     */
    Optional<Permission> findByCode(String code);
}
