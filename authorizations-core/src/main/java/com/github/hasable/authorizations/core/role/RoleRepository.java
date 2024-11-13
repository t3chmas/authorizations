package com.github.hasable.authorizations.core.role;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository {

  /**
   * Find all permissions
   *
   * @return A Set of permissions, may be empty but never null
   */
  Set<Role> findAll();

  /**
   * Find a permission by its code
   *
   * @return The permission, if found
   */
  Optional<Role> findByCode(String code);

  /** Store a permission. If the object does not exist, create it, else update it. */
  void store(Role role);
}
