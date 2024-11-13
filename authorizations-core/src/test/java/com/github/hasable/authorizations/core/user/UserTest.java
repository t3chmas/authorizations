package com.github.hasable.authorizations.core.user;

import com.github.hasable.authorizations.core.permission.Permission;
import com.github.hasable.authorizations.core.permission.PermissionTest;
import com.github.hasable.authorizations.core.role.Role;
import com.github.hasable.authorizations.core.role.RoleTest;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

  @Test
  public void TestUserHasPermission() {

    Permission p1, p2, p3, p4, p5, p6;
    p1 = PermissionTest.random();
    p2 = PermissionTest.random();
    p3 = PermissionTest.random();
    p4 = PermissionTest.random();
    p5 = PermissionTest.random();

    Role r1 = RoleTest.random();
    r1.addPermission(p1);
    r1.addPermission(p2);

    Role r2 = RoleTest.random();
    r2.addPermission(p3);

    User u =
        User.builder()
            .login(RandomStringUtils.randomAlphanumeric(32))
            .roles(Set.of(r1, r2))
            .permissions(Set.of(p2, p4))
            .build();

    Assertions.assertTrue(u.hasPermission(p1));
    Assertions.assertTrue(u.hasPermission(p2));
    Assertions.assertTrue(u.hasPermission(p3));
    Assertions.assertTrue(u.hasPermission(p4));
    Assertions.assertFalse(u.hasPermission(p5));
  }
}
