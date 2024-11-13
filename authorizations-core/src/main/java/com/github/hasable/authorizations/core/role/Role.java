package com.github.hasable.authorizations.core.role;

import com.github.hasable.authorizations.core.permission.Permission;
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
public class Role {

  @NonNull @EqualsAndHashCode.Include private String code;

  private String description;

  @Builder.Default private Set<Permission> permissions = new HashSet<>();

  public Role(String code) {
    this(code, null, new HashSet<>());
  }

  public Role(String code, String description) {
    this(code, description, new HashSet<>());
  }

  public void addPermission(Permission p) {
    this.getPermissions().add(p);
  }
}
