package com.github.t3chmas.authorizations.storage.role;

import com.github.t3chmas.authorizations.storage.permission.PermissionRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RoleTest {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    public static RoleEntity createRandomRole() {
        RoleEntity random = new RoleEntity();
        random.setCode(RandomStringUtils.randomAlphanumeric(255));
        random.setDescription(RandomStringUtils.randomAlphanumeric(4096));
        return random;
    }
}
