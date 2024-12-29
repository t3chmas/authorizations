package com.github.hasable.authorizations.storage.permission;

import com.github.hasable.authorizations.storage.common.Traceable;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity(name = "permission")
public class PermissionEntity extends Traceable {

  /** Primary key */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_id_seq")
  @SequenceGenerator(name = "permission_id_seq", allocationSize = 1)
  private Long id;

  @Nonnull @EqualsAndHashCode.Include private String code;

  @Lob private String description;
}
