package com.github.hasable.authorizations.storage.permission;

import com.github.hasable.authorizations.core.permission.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

  PermissionEntity permissionToPermissionEntity(Permission p);

  Permission permissionEntityToPermission(PermissionEntity p);
}
