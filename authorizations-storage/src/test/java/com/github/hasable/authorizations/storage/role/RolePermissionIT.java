package com.github.hasable.authorizations.storage.role;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.hasable.authorizations.storage.permission.PermissionEntity;
import com.github.hasable.authorizations.storage.permission.PermissionRepository;
import com.github.hasable.authorizations.storage.permission.PermissionTest;
import jakarta.persistence.EntityManager;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RolePermissionIT {

  @Autowired RoleRepository roleRepository;

  @Autowired PermissionRepository permissionRepository;

  @Autowired RolePermissionRepository rolePermissionRepository;

  @Autowired private EntityManager entityManager;

  @Test
  public void testRolePermission() {

    // given not yet created
    RoleEntity role = roleRepository.save(RoleIT.createRandomRole());

    PermissionEntity p1, p2;
    p1 = permissionRepository.save(PermissionTest.createRandomPermission());
    p2 = permissionRepository.save(PermissionTest.createRandomPermission());

    // when permissions have been set to role
    RolePermissionEntity rolePermission = new RolePermissionEntity();
    rolePermission.setRole(role);
    rolePermission.setPermission(p1);
    rolePermissionRepository.save(rolePermission);

    rolePermission = new RolePermissionEntity();
    rolePermission.setRole(role);
    rolePermission.setPermission(p2);
    rolePermissionRepository.save(rolePermission);

    entityManager.flush();

    // then role should have permissions
    entityManager.refresh(role);
    Set<PermissionEntity> s =
        role.getRolePermission().stream()
            .map(RolePermissionEntity::getPermission)
            .collect(Collectors.toSet());

    assertTrue(s.contains(p1));
    assertTrue(s.contains(p2));
  }
}
