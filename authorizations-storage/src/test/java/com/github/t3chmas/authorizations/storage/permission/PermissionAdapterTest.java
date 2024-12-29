package com.github.hasable.authorizations.storage.permission;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.hasable.authorizations.core.permission.Permission;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
public class PermissionAdapterTest {

  @Autowired @InjectMocks PermissionAdapter permissionAdapter;

  // needed to inject into permissionAdapter, as I do not want to load a full Spring context for a
  // unit test.
  @Spy PermissionMapper permissionMapper = new PermissionMapperImpl();
  @Mock PermissionRepository repository;

  @Test
  public void testFindAll() {
    // given ...
    HashMap<String, PermissionEntity> expectedMap = new HashMap<>();
    PermissionEntity pe1 = PermissionTest.createRandomPermission();
    expectedMap.put(pe1.getCode(), pe1);
    PermissionEntity pe2 = PermissionTest.createRandomPermission();
    expectedMap.put(pe2.getCode(), pe2);

    when(this.repository.findAll()).thenReturn(List.of(pe1, pe2));

    Set<Permission> permissions = this.permissionAdapter.findAll();
    assertEquals(2, permissions.size());

    for (Permission p : permissions) {
      PermissionEntity expected = expectedMap.get(p.getCode());
      assertNotNull(expected);
      assertEquals(expected.getCode(), p.getCode());
      assertEquals(expected.getDescription(), p.getDescription());
    }
  }

  @Test
  public void testFindByCode() {
    // given ...
    PermissionEntity pe1 = PermissionTest.createRandomPermission();

    // then
    when(this.repository.findByCode(anyString()))
        .thenAnswer(
            invocation -> {
              if (invocation.getArgument(0).equals(pe1.getCode())) return Optional.of(pe1);
              else return Optional.empty();
            });

    assertTrue(
        this.permissionAdapter.findByCode(RandomStringUtils.randomAlphanumeric(255)).isEmpty());

    Permission result = this.permissionAdapter.findByCode(pe1.getCode()).orElseThrow();
    assertEquals(pe1.getCode(), result.getCode());
    assertEquals(pe1.getDescription(), result.getDescription());
  }
}
