package com.github.hasable.authorizations.storage.user;

import com.github.hasable.authorizations.storage.common.Traceable;
import com.github.hasable.authorizations.storage.role.RoleEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "user_permission")
public class UserPermissionEntity extends Traceable {

  @EmbeddedId private UserPermissionId id = new UserPermissionId();

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @ManyToOne
  @MapsId("permissionId")
  @JoinColumn(name = "permission_id")
  private RoleEntity role;
}
