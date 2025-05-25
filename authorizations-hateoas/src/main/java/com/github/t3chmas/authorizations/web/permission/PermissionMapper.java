package com.github.t3chmas.authorizations.web.permission;

import com.github.t3chmas.authorizations.core.permission.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "PermissionWebMapper")
public interface PermissionMapper {

    PermissionDTO permissionToPermissionDTO(Permission p);

    Permission permissionDTOToPermission(PermissionDTO p);
}
