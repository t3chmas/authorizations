package com.github.hasable.authorizations.web.domain.permission;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class PermissionDTO {

  @NonNull @EqualsAndHashCode.Include private String code;

  private String description;

  public PermissionDTO(final String code) {
    this(code, null);
  }
}
