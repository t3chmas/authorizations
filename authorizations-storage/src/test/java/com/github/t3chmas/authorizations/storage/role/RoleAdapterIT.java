package com.github.t3chmas.authorizations.storage.role;

import com.github.t3chmas.authorizations.core.permission.Permission;
import com.github.t3chmas.authorizations.core.role.Role;
import com.github.t3chmas.authorizations.storage.permission.PermissionAdapter;
import com.github.t3chmas.authorizations.storage.permission.PermissionEntity;
import com.github.t3chmas.authorizations.storage.permission.PermissionEntityTest;
import com.github.t3chmas.authorizations.storage.permission.PermissionRepository;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@DataJpaTest
//@Import({LiquibaseConfig.class, RoleAdapter.class, RoleStorageMapper.class, PermissionStorageMapper.class, PermissionAdapter.class})
public class RoleAdapterIT {

    @Autowired
    RoleAdapter roleAdapter;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RolePermissionRepository rolePermissionRepository;

    @Autowired
    PermissionAdapter permissionAdapter;

    @Autowired
    EntityManager entityManager;

    @Test
    public void testCreateRole() {

        // given 2 permissions
        PermissionEntity pe1, pe2, pe3;
        pe1 = this.permissionRepository.save(PermissionEntityTest.createRandomPermission());
        pe2 = this.permissionRepository.save(PermissionEntityTest.createRandomPermission());
        Permission p1, p2;
        p1 = this.permissionAdapter.findByCode(pe1.getCode()).orElseThrow();
        p2 = this.permissionAdapter.findByCode(pe2.getCode()).orElseThrow();
        this.permissionRepository.flush();

        // when I create and save a role without permission
        Role roleWithoutPermissions = Role.builder()
            .code(RandomStringUtils.insecure().nextAlphanumeric(8)).build();
        Role created = this.roleAdapter.save(roleWithoutPermissions);

        // then
        assertEquals(roleWithoutPermissions.getCode(), created.getCode());
        assertNull(created.getDescription());
        assertNull(created.getPermissions());

        // when I create and save a role with permissions
        Role roleWithPermissions = Role.builder()
            .code(RandomStringUtils.insecure().nextAlphanumeric(8))
            .permissions(Set.of(p1, p2))
            .build();
        created = this.roleAdapter.save(roleWithPermissions);

        // then
        assertEquals(roleWithPermissions.getCode(), created.getCode());
        assertNull(created.getDescription());
        assertTrue(created.hasPermission(p1.getCode()));
        assertTrue(created.hasPermission(p2.getCode()));
    }

    @Test
    public void testUpdateExistingRole() {

        // given 3 permissions
        PermissionEntity pe1, pe2, pe3;
        pe1 = this.permissionRepository.save(PermissionEntityTest.createRandomPermission());
        pe2 = this.permissionRepository.save(PermissionEntityTest.createRandomPermission());
        pe3 = this.permissionRepository.save(PermissionEntityTest.createRandomPermission());
        this.permissionRepository.flush();

        // given a single role
        RoleEntity re = this.roleRepository.saveAndFlush(RoleEntityTest.createRandomRole());

        // given role has permissions pe1 and pe2
        RolePermissionEntity rpe1 = rolePermissionRepository.save(RolePermissionEntity.builder().role(re).permission(pe1).build());
        RolePermissionEntity rpe2 = rolePermissionRepository.save(RolePermissionEntity.builder().role(re).permission(pe2).build());
        rolePermissionRepository.flush();
        this.entityManager.clear();

        // when I get the role
        Role role = this.roleAdapter.findByCode(re.getCode(), true).orElseThrow();
        assertTrue(role.hasPermission(pe1.getCode()));
        assertTrue(role.hasPermission(pe2.getCode()));
        assertFalse(role.hasPermission(pe3.getCode()));

        // when I change its permissions and save it
        Permission p2, p3;
        p2 = this.permissionAdapter.findByCode(pe2.getCode()).orElseThrow();
        p3 = this.permissionAdapter.findByCode(pe3.getCode()).orElseThrow();
        role.setPermissions(Set.of(p2, p3));
        this.roleAdapter.save(role);
        this.entityManager.clear();

        // then
        Role tested = this.roleAdapter.findByCode(role.getCode(), true).orElseThrow();
        assertFalse(role.hasPermission(pe1.getCode()));
        assertTrue(role.hasPermission(pe2.getCode()));
        assertTrue(role.hasPermission(pe3.getCode()));

    }

    @Test
    public void testRemoveRoleWithPermissions() {
        // given 2 permissions
        PermissionEntity pe1, pe2;
        pe1 = this.permissionRepository.save(PermissionEntityTest.createRandomPermission());
        pe2 = this.permissionRepository.save(PermissionEntityTest.createRandomPermission());

        // given 1 role without permission
        RoleEntity re1 = this.roleRepository.save(RoleEntityTest.createRandomRole());

        // given 1 role with permissions
        RoleEntity re2 = this.roleRepository.save(RoleEntityTest.createRandomRole());
        this.rolePermissionRepository.save(RolePermissionEntity.builder().role(re2).permission(this.permissionRepository.save(PermissionEntityTest.createRandomPermission())).build());
        this.rolePermissionRepository.save(RolePermissionEntity.builder().role(re2).permission(this.permissionRepository.save(PermissionEntityTest.createRandomPermission())).build());

        // when I remove a role without permission then it should be missing
        this.roleAdapter.remove(this.roleAdapter.findByCode(re1.getCode()).orElseThrow().getCode());
        assertTrue(this.roleAdapter.findByCode(re1.getCode()).isEmpty());

        // when I remove a role with permissions then it should be missing
        this.roleAdapter.remove(this.roleAdapter.findByCode(re2.getCode()).orElseThrow().getCode());
        assertTrue(this.roleAdapter.findByCode(re2.getCode()).isEmpty());
    }
}
