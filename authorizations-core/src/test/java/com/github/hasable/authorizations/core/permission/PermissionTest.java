package com.github.hasable.authorizations.core.permission;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PermissionTest {

  public static Permission random() {
    return new Permission(
        RandomStringUtils.randomAlphanumeric(256), RandomStringUtils.randomAlphanumeric(1024));
  }

  @Test
  public void TestPermission() {
    String code = RandomStringUtils.randomAlphanumeric(256);
    String description = RandomStringUtils.randomAlphanumeric(1024);

    Permission testedOne;
    testedOne = new Permission(code);
    Assertions.assertEquals(code, testedOne.getCode());
    Assertions.assertNull(testedOne.getDescription());

    try {
      testedOne = new Permission(null);
      Assertions.fail("<IllegalArgumentException> was expected");
    } catch (NullPointerException e) {
      // ok with that
    }

    testedOne = new Permission(code, description);
    Assertions.assertEquals(code, testedOne.getCode());
    Assertions.assertEquals(description, testedOne.getDescription());
  }
}
