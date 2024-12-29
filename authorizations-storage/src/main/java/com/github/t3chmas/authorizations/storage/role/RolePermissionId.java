package com.github.t3chmas.authorizations.storage.role;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RolePermissionId {

    private long roleId;

    private long permissionId;
}
