package com.github.hasable.authorizations.storage.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  /**
   * Find a role by its login
   *
   * @param login the searched code
   * @return the user if found
   */
  Optional<UserEntity> findByLogin(String login);
}
