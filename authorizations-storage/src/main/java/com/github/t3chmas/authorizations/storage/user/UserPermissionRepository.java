package com.github.t3chmas.authorizations.storage.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPermissionRepository
    extends JpaRepository<UserPermissionEntity, UserPermissionId> {}
