package com.github.hasable.authorizations.core.role;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoleTest {

  public static Role random() {
    return new Role(
        RandomStringUtils.randomAlphanumeric(256), RandomStringUtils.randomAlphanumeric(1024));
  }

  @Test
  public void TestRole() {
    String code = RandomStringUtils.randomAlphanumeric(256);
    String description = RandomStringUtils.randomAlphanumeric(1024);

    Role testedOne;
    testedOne = new Role(code);
    Assertions.assertEquals(code, testedOne.getCode());
    Assertions.assertNull(testedOne.getDescription());

    try {
      testedOne = new Role(null);
      Assertions.fail("<IllegalArgumentException> was expected");
    } catch (NullPointerException e) {
      // ok with that
    }

    testedOne = new Role(code, description);
    Assertions.assertEquals(code, testedOne.getCode());
    Assertions.assertEquals(code, testedOne.getCode());
    Assertions.assertEquals(description, testedOne.getDescription());
  }
}
