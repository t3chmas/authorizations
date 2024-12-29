package com.github.t3chmas.authorizations.core.user;

import com.github.t3chmas.authorizations.core.permission.Permission;
import com.github.t3chmas.authorizations.core.role.Role;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class User {

    private @NonNull
    @EqualsAndHashCode.Include String login;

    private Set<Role> roles;

    private Set<Permission> permissions;

    public boolean hasPermission(String permissionCode) {
        return this.permissions.stream().map(Permission::getCode).anyMatch(permissionCode::equals)
            || this.roles.stream()
            .map(Role::getPermissions)
            .flatMap(Set::stream)
            .map(Permission::getCode)
            .anyMatch(permissionCode::equals);
    }

    public boolean hasPermission(Permission p) {
        return this.hasPermission(p.getCode());
    }
}
