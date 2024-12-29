package com.github.hasable.authorizations.storage.role;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

  /**
   * Find a role by its code
   *
   * @param code the searched code
   * @return the right if found
   */
  Optional<RoleEntity> findByCode(String code);
}
