package com.github.hasable.authorizations.storage.user;

import com.github.hasable.authorizations.storage.common.Traceable;
import com.github.hasable.authorizations.storage.role.RoleEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "user_role")
public class UserRoleEntity extends Traceable {

  @EmbeddedId private UserRoleId id = new UserRoleId();

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @ManyToOne
  @MapsId("roleId")
  @JoinColumn(name = "role_id")
  private RoleEntity role;
}
