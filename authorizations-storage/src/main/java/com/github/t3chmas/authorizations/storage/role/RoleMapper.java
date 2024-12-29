package com.github.t3chmas.authorizations.storage.role;

import com.github.t3chmas.authorizations.core.role.Role;
import com.github.t3chmas.authorizations.storage.permission.PermissionMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", implementationName = "RoleStorageMapper", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = PermissionMapper.class)
public abstract class RoleMapper {

    PermissionMapper permissionMapper;

    @Mapping(target = "id", ignore = true)
    public abstract RoleEntity roleToRoleEntity(Role r);

    @Mapping(target = "permissions", ignore = true)
    public abstract Role roleEntityToRole(RoleEntity re);

    public Role roleEntityToRole(RoleEntity re, boolean includePermissions) {
        Role r = roleEntityToRole(re);
        if (includePermissions) {
            r.setPermissions(re.getRolePermission().stream()
                .map(rolePermissionEntity -> permissionMapper.permissionEntityToPermission(rolePermissionEntity.getPermission()))
                .collect(Collectors.toSet()));
        }
        return r;
    }
}
