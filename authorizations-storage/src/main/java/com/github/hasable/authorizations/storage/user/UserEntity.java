package com.github.hasable.authorizations.storage.user;

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
@Entity(name = "user")
public class UserEntity extends Traceable {

  /** Primary Key */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
  @SequenceGenerator(name = "user_id_seq", allocationSize = 1)
  private Long id;

  @Nonnull @EqualsAndHashCode.Include private String login;
}
