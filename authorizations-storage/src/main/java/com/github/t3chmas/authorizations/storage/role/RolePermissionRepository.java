package com.github.t3chmas.authorizations.storage.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository
    extends JpaRepository<RolePermissionEntity, RolePermissionId> {

    long deleteByRoleId(long roleId);

}
