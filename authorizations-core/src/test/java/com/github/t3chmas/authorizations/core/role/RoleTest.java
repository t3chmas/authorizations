package com.github.t3chmas.authorizations.core.role;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoleTest {

    public static Role random() {
        return Role.builder().code(RandomStringUtils.randomAlphanumeric(256)).description(RandomStringUtils.randomAlphanumeric(1024)).build();
    }

    @Test
    public void TestRole() {
        try {
            Role.builder().code(null).build();
            Assertions.fail("<NullPointerException> was expected");
        } catch (NullPointerException e) {
            // ok with that
        }
    }
}
