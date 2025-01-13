package com.github.t3chmas.authorizations.storage.permission;

import org.apache.commons.lang3.RandomStringUtils;

public class PermissionEntityTest {

    public static PermissionEntity createRandomPermission() {
        return PermissionEntity.builder()
            .code(RandomStringUtils.insecure().nextAlphanumeric(8))
            .description(RandomStringUtils.insecure().nextAlphanumeric(32))
            .build();
    }
}
