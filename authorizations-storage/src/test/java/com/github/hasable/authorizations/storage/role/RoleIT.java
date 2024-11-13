package com.github.hasable.authorizations.storage.role;

import static org.junit.jupiter.api.Assertions.*;

import com.github.hasable.authorizations.storage.permission.PermissionEntity;
import com.github.hasable.authorizations.storage.permission.PermissionRepository;
import com.github.hasable.authorizations.storage.permission.PermissionTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RoleIT {
  @Autowired RoleRepository roleRepository;

  @Autowired PermissionRepository permissionRepository;

  public static RoleEntity createRandomRole() {
    RoleEntity random = new RoleEntity();
    random.setCode(RandomStringUtils.randomAlphanumeric(255));
    random.setDescription(RandomStringUtils.randomAlphanumeric(4096));
    return random;
  }

  @Test
  public void testRoleWithoutPermissions() {

    // given not yet created
    RoleEntity subject = RoleIT.createRandomRole();
    // then
    assertNull(subject.getId());

    // when created
    this.roleRepository.saveAndFlush(subject);

    // then
    assertNotNull(subject.getId());
  }

  @Test
  public void testRoleWithPermissions() {

    // given permissions exist
    PermissionEntity pe1, pe2;
    pe1 = PermissionTest.createRandomPermission();
    pe2 = PermissionTest.createRandomPermission();
    this.permissionRepository.saveAllAndFlush(Lists.list(pe1, pe2));

    // given role does not exist
    RoleEntity subject = RoleIT.createRandomRole();

    // when assigning permissions to role
    subject
        .getRolePermission()
        .add(RolePermissionEntity.builder().role(subject).permission(pe1).build());
    subject
        .getRolePermission()
        .add(RolePermissionEntity.builder().role(subject).permission(pe2).build());
    this.roleRepository.saveAndFlush(subject);

    // then role should exist with permissions
    RoleEntity tested = this.roleRepository.findByCode(subject.getCode()).orElseThrow();
    assertEquals(2, tested.getRolePermission().size());
    for (RolePermissionEntity rpe : tested.getRolePermission()) {
      assertTrue(rpe.getPermission().equals(pe1) || rpe.getPermission().equals(pe2));
    }
  }
}
