package com.github.hasable.authorizations.web.domain.permission;

import com.github.hasable.authorizations.core.permission.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

  PermissionDTO permissionToPermissionDTO(Permission p);

  Permission permissionDTOToPermission(PermissionDTO p);
}
