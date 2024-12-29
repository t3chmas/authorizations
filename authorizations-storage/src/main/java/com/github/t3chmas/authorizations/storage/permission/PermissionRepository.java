package com.github.hasable.authorizations.storage.permission;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

  /**
   * Find a permission by its code
   *
   * @param code the searched code
   * @return the right if found
   */
  Optional<PermissionEntity> findByCode(String code);
}
