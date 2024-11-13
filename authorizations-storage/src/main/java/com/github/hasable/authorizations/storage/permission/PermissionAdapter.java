package com.github.hasable.authorizations.storage.permission;

import com.github.hasable.authorizations.core.permission.Permission;
import com.github.hasable.authorizations.core.permission.PermissionPort;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("com.github.hasable.authorizations.storage.permission.PermissionAdapter")
public class PermissionAdapter implements PermissionPort {

  PermissionMapper mapper;

  PermissionRepository repository;

  @Autowired
  public PermissionAdapter(PermissionRepository repository, PermissionMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public Set<Permission> findAll() {
    return this.repository.findAll().stream()
        .map(mapper::permissionEntityToPermission)
        .collect(Collectors.toSet());
  }

  @Override
  public Page<Permission> findAll(Pageable p) {
    return this.repository.findAll(p).map(mapper::permissionEntityToPermission);
  }

  @Override
  public Optional<Permission> findByCode(String code) {
    return repository.findByCode(code).map(mapper::permissionEntityToPermission);
  }
}
