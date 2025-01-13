package com.github.t3chmas.authorizations.core.role;

import com.github.t3chmas.authorizations.core.permission.Permission;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Role {

    @NonNull
    @EqualsAndHashCode.Include
    private String code;

    private String description;

    private Set<Permission> permissions;

    public void addPermission(Permission p) {
        if (this.permissions == null)
            this.permissions = new HashSet<>();
        this.getPermissions().add(p);
    }

    public boolean hasPermission(Permission permission) {
        for (Permission p : permissions) {
            if (p.equals(permission))
                return true;
        }
        return false;
    }

    public boolean hasPermission(String permissionCode) {
        for (Permission p : permissions) {
            if (p.getCode().equals(permissionCode))
                return true;
        }
        return false;
    }
}
