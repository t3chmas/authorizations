package com.github.hasable.authorizations.core.permission;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Permission {

  @NonNull @EqualsAndHashCode.Include private String code;

  private String description;

  public Permission(String code) {
    this(code, null);
  }
}
