package com.github.t3chmas.authorizations.storage.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

    /**
     * Find a permission by its code
     *
     * @param code the searched code
     * @return the right if found
     */
    Optional<PermissionEntity> findByCode(String code);
}
