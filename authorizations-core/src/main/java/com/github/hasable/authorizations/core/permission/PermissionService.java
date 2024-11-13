package com.github.hasable.authorizations.core.permission;

import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@AllArgsConstructor
@Service
public class PermissionService {

  private final Logger logger = LoggerFactory.getLogger(PermissionService.class);

  private PermissionPort repository;

  /**
   * Find all permissions
   *
   * @return A Set of permissions, may be empty but never null
   */
  public Set<Permission> findAll() {
    logger.trace("Finding all permissions...");
    Set<Permission> permissions = this.repository.findAll();
    logger.trace("Found {} permissions!", permissions.size());
    return permissions;
  }

  public Page<Permission> findAll(Pageable p) {
    logger.trace("Finding permissions from page {}", p.toString());
    Page<Permission> permissions = this.repository.findAll(p);
    logger.trace("Found {} permissions!", permissions.getSize());
    return permissions;
  }

  /**
   * Find a permission by its code
   *
   * @return The permission, if found
   */
  public Optional<Permission> findByCode(String code) {
    if (!StringUtils.hasText(code)) throw new IllegalArgumentException("<code> must not be blank");
    logger.trace("Looking for permission with code={}", code);
    Optional<Permission> permission = this.repository.findByCode(code);
    if (permission.isPresent()) logger.trace("Found permission {}", permission);
    else logger.trace("Found nothing");
    return permission;
  }
}
