package com.github.t3chmas.authorizations.storage.permission;

import com.github.t3chmas.authorizations.storage.config.LiquibaseConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(LiquibaseConfig.class)
public class PermissionIT {

    @Autowired
    PermissionRepository permissionRepository;

    /**
     * Test createdAt and modifiedAt fields.
     */
    @Test
    public void testCommon() {

        // Given
        PermissionEntity subject = new PermissionEntity();
        subject.setCode(RandomStringUtils.randomAlphanumeric(255));
        subject.setDescription(RandomStringUtils.randomAlphanumeric(4096));

        // when not created

        // then
        assertNull(subject.getCreatedAt());
        assertNull(subject.getModifiedAt());

        // when created
        this.permissionRepository.saveAndFlush(subject);

        // then
        Instant created = subject.getCreatedAt();
        assertNotNull(created);

        Instant firstModified = subject.getModifiedAt();
        assertNotNull(firstModified);

        // Given not yet modified
        // then
        assertEquals(created, firstModified);

        // when modified
        subject.setDescription(RandomStringUtils.randomAlphanumeric(4096));
        this.permissionRepository.saveAndFlush(subject);

        // then
        Assertions.assertEquals(created, subject.getCreatedAt());
        Assertions.assertNotEquals(created, subject.getModifiedAt());
        assertTrue(firstModified.isBefore(subject.getModifiedAt()));
    }

    @Test
    public void testPermission() {

        // given not yet created
        PermissionEntity subject = new PermissionEntity();
        subject.setCode(RandomStringUtils.randomAlphanumeric(255));
        subject.setDescription(RandomStringUtils.randomAlphanumeric(4096));
        assertNull(subject.getId());

        // when created
        this.permissionRepository.saveAndFlush(subject);
        PermissionEntity retrieved =
            this.permissionRepository.findByCode(subject.getCode()).orElseThrow();

        // then
        assertNotNull(subject.getId());
        assertEquals(subject.getCode(), retrieved.getCode());
        assertEquals(subject.getDescription(), retrieved.getDescription());
    }
}
