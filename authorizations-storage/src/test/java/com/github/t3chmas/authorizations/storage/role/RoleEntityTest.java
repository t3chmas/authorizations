package com.github.t3chmas.authorizations.storage.role;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RoleEntityTest {

    public static RoleEntity createRandomRole() {
        return RoleEntity.builder()
            .code(RandomStringUtils.insecure().nextAlphanumeric(8))
            .description(RandomStringUtils.insecure().nextAlphanumeric(32))
            .build();
    }
}
