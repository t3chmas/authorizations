package com.github.t3chmas.authorizations.web.role;

import com.github.t3chmas.authorizations.core.permission.Permission;
import com.github.t3chmas.authorizations.core.role.Role;
import org.mapstruct.Mapper;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", implementationName = "RoleWebMapper")
public interface RoleMapper {

    RoleDTO toDTO(Role r);

    default Set<String> toDTO(Set<Permission> permissions) {
        return Optional.ofNullable(permissions)
            .map(p -> p.stream()
                .map(Permission::getCode)
                .collect(Collectors.toSet()))
            .orElse(null);
    }

    Role fromDTO(RoleDTO p);

    default Set<Permission> fromDTO(Set<String> permissions) {
        return Optional.ofNullable(permissions)
            .map(p -> p.stream()
                .map(Permission::new)
                .collect(Collectors.toSet()))
            .orElse(null);
    }
}