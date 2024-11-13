package com.github.hasable.authorizations.storage.role;

import com.github.hasable.authorizations.storage.common.Traceable;
import com.github.hasable.authorizations.storage.permission.PermissionEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "role_permission")
@Builder
public class RolePermissionEntity extends Traceable {

  @EmbeddedId private final RolePermissionId id = new RolePermissionId();

  @ManyToOne
  @MapsId("roleId")
  @JoinColumn(name = "role_id")
  private RoleEntity role;

  @ManyToOne
  @MapsId("permissionId")
  @JoinColumn(name = "permission_id")
  private PermissionEntity permission;
}
