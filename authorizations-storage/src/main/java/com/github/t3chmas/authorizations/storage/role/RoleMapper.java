package com.github.t3chmas.authorizations.storage.role;

import com.github.t3chmas.authorizations.core.role.Role;
import com.github.t3chmas.authorizations.storage.permission.PermissionMapper;
import lombok.Setter;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Had to use "setter" to inject PermissionMapper, see <a href="https://github.com/mapstruct/mapstruct/issues/2257">support component injection in abstract base class issue</a>.
 */
@Mapper(componentModel = "spring", implementationName = "RoleStorageMapper", uses = {PermissionMapper.class}, injectionStrategy = InjectionStrategy.FIELD)
@Setter
public abstract class RoleMapper {

    @Autowired
    protected PermissionMapper permissionMapper;

    public abstract RoleEntity roleToRoleEntity(Role r);

    /**
     * Update RoleEntity based on Role r
     *
     * @param r  the role to map
     * @param re the roleEntity to update
     * @return the updated RoleEntity
     */
    public abstract RoleEntity roleToRoleEntity(Role r, @MappingTarget RoleEntity re);

    @Mapping(target = "permissions", ignore = true)
    public abstract Role roleEntityToRole(RoleEntity re);

    public Role roleEntityToRole(RoleEntity re, boolean includePermissions) {
        Role r = roleEntityToRole(re);
        if (includePermissions) {
            r.setPermissions(Optional.ofNullable(re.getRolePermission())
                .orElseGet(Collections::emptySet)
                .stream()
                .map(rolePermissionEntity -> permissionMapper.permissionEntityToPermission(rolePermissionEntity.getPermission()))
                .collect(Collectors.toSet()));
        }
        return r;
    }
}
