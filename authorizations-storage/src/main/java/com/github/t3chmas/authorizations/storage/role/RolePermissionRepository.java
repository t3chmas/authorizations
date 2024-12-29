package com.github.hasable.authorizations.storage.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository
    extends JpaRepository<RolePermissionEntity, RolePermissionId> {}
