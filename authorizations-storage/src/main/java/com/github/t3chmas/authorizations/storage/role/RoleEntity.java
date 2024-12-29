package com.github.hasable.authorizations.storage.role;

import com.github.hasable.authorizations.storage.common.Traceable;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity(name = "role")
public class RoleEntity extends Traceable {

  /** Primary key */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
  @SequenceGenerator(name = "role_id_seq", allocationSize = 1)
  private Long id;

  @Nonnull @EqualsAndHashCode.Include private String code;

  @Lob private String description;

  @OneToMany(mappedBy = "role")
  private Set<RolePermissionEntity> rolePermission;

  public Set<RolePermissionEntity> getRolePermission() {
    if (this.rolePermission == null) this.rolePermission = new HashSet<>();
    return rolePermission;
  }
}
