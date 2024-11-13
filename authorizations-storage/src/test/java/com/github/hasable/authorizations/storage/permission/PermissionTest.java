package com.github.hasable.authorizations.storage.permission;

import org.apache.commons.lang3.RandomStringUtils;

public class PermissionTest {

  public static PermissionEntity createRandomPermission() {
    return PermissionEntity.builder()
        .code(RandomStringUtils.randomAlphanumeric(255))
        .description(RandomStringUtils.randomAlphanumeric(4096))
        .build();
  }
}
