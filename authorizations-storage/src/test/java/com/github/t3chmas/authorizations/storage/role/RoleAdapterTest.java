package com.github.t3chmas.authorizations.storage.role;

import com.github.t3chmas.authorizations.core.role.Role;
import com.github.t3chmas.authorizations.storage.permission.PermissionEntityTest;
import com.github.t3chmas.authorizations.storage.permission.PermissionStorageMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleAdapterTest {

    @Autowired
    @InjectMocks
    RoleAdapter roleAdapter;

    // needed to inject into permissionAdapter, as I do not want to load a full Spring context for a
    // unit test.
    @Spy
    RoleMapper roleMapper;

    @Mock
    RoleRepository roleRepository;

    {
        roleMapper = new RoleStorageMapper();
        roleMapper.setPermissionMapper(new PermissionStorageMapper());
    }

    @Test
    public void testFindAll() {
        RoleEntity re1 = RoleEntityTest.createRandomRole();
        re1.setRolePermission(Set.of(
            RolePermissionEntity.builder().role(re1).permission(PermissionEntityTest.createRandomPermission()).build(),
            RolePermissionEntity.builder().role(re1).permission(PermissionEntityTest.createRandomPermission()).build())
        );

        when(this.roleRepository.findAll()).thenReturn(List.of(re1));

        Set<Role> roles = this.roleAdapter.findAll();
        assertEquals(1, roles.size());
        for (Role r : roles) {
            assertEquals(re1.getCode(), r.getCode());
            // tests that permissions are not fetched
            assertNull(r.getPermissions());
        }

        Pageable pageable = PageRequest.of(0, 8);
        PageImpl<RoleEntity> pi = new PageImpl<>(List.of(re1), pageable, 1);

        when(this.roleRepository.findAll(any(Pageable.class))).thenReturn(pi);
        Page<Role> pRole = this.roleAdapter.findAll(pageable);

        assertEquals(1, pRole.getContent().size());
        for (Role r : pRole.getContent()) {
            assertEquals(re1.getCode(), r.getCode());
            // tests that permissions are not fetched
            assertNull(r.getPermissions());
        }
    }

    @Test
    public void testFindByCode() {
        RoleEntity roleWithoutPermissions = RoleEntityTest.createRandomRole();
        String code = roleWithoutPermissions.getCode();

        RoleEntity roleWithPermissions = RoleEntity.builder().code(code).description(roleWithoutPermissions.getDescription()).build();
        roleWithPermissions.setRolePermission(Set.of(
            RolePermissionEntity.builder().role(roleWithPermissions).permission(PermissionEntityTest.createRandomPermission()).build(),
            RolePermissionEntity.builder().role(roleWithPermissions).permission(PermissionEntityTest.createRandomPermission()).build())
        );

        when(this.roleRepository.findByCode(anyString(), anyBoolean())).thenReturn(Optional.empty());
        when(this.roleRepository.findByCode(code, false)).thenReturn(Optional.of(roleWithoutPermissions));
        when(this.roleRepository.findByCode(code, true)).thenReturn(Optional.of(roleWithPermissions));

        Optional<Role> role = this.roleAdapter.findByCode(RandomStringUtils.randomAlphanumeric(255));
        assertTrue(role.isEmpty());

        role = this.roleAdapter.findByCode(code);
        assertNull(role.orElseThrow().getPermissions());

        role = this.roleAdapter.findByCode(code, true);
        assertNotNull(role.orElseThrow().getPermissions());
    }

}
