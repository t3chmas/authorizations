package com.github.t3chmas.authorizations.storage.permission;

import com.github.t3chmas.authorizations.core.permission.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "PermissionStorageMapper")
public interface PermissionMapper {

    PermissionEntity permissionToPermissionEntity(Permission p);

    Permission permissionEntityToPermission(PermissionEntity p);
}
