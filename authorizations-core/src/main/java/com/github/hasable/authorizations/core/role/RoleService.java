package com.github.hasable.authorizations.core.role;

import java.util.Set;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleService {

  private final Logger logger = LoggerFactory.getLogger(RoleRepository.class);

  private RoleRepository roleRepository;

  public Set<Role> findAll() {
    logger.trace("Finding all roles...");
    Set<Role> roles = this.roleRepository.findAll();
    logger.trace("Found {} roles!", roles.size());
    return roles;
  }
}
