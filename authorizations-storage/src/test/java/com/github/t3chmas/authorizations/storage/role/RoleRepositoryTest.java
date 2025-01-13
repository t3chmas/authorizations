package com.github.t3chmas.authorizations.storage.role;

import com.github.t3chmas.authorizations.storage.permission.PermissionEntity;
import com.github.t3chmas.authorizations.storage.permission.PermissionEntityTest;
import com.github.t3chmas.authorizations.storage.permission.PermissionRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RolePermissionRepository rolePermissionRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    SessionFactory sessionFactory;

    /**
     * Tests that normal behavior is n+1 queries to get a role with its permissions
     */
    @Test
    public void testFindOneWithoutGettingPermissions() {
        // given not yet created
        PermissionEntity pe1 = this.permissionRepository.save(PermissionEntityTest.createRandomPermission());
        PermissionEntity pe2 = this.permissionRepository.save(PermissionEntityTest.createRandomPermission());

        RoleEntity base = RoleEntityTest.createRandomRole();
        base.setRolePermission(Set.of(new RolePermissionEntity(base, pe1), new RolePermissionEntity(base, pe2)));
        base = this.roleRepository.saveAndFlush(base);
        this.rolePermissionRepository.saveAllAndFlush(List.of(new RolePermissionEntity(base, pe1), new RolePermissionEntity(base, pe2)));

        // when clear first level cache
        entityManager.clear();

        long beforeRole, afterRole, afterPermissions;

        // First guess was sessionFactory.getStatistics().getQueryExecutionCount(), but it does not work as I expected
        beforeRole = sessionFactory.getStatistics().getPrepareStatementCount();
        RoleEntity subject = this.roleRepository.findByCode(base.getCode(), false).orElseThrow();
        afterRole = sessionFactory.getStatistics().getPrepareStatementCount();
        assertEquals(base.getCode(), subject.getCode());
        assertEquals(beforeRole + 1, afterRole);

        // do something with permission or it won't get fetched
        int permissionsSize = subject.getRolePermission().stream()
            .map(RolePermissionEntity::getPermission).collect(Collectors.toSet()).size();
        assertEquals(2, permissionsSize);

        afterPermissions = sessionFactory.getStatistics().getPrepareStatementCount();
        assertEquals(afterRole + 1, afterPermissions);
    }

    /**
     * Tests that one query is enough to get a role with its permissions
     */
    @Test
    public void testFindOneWithGettingPermissions() {
        // given not yet created
        PermissionEntity pe1 = this.permissionRepository.save(PermissionEntityTest.createRandomPermission());
        PermissionEntity pe2 = this.permissionRepository.save(PermissionEntityTest.createRandomPermission());

        RoleEntity base = RoleEntityTest.createRandomRole();
        base.setRolePermission(Set.of(new RolePermissionEntity(base, pe1), new RolePermissionEntity(base, pe2)));
        base = this.roleRepository.saveAndFlush(base);
        this.rolePermissionRepository.saveAllAndFlush(List.of(new RolePermissionEntity(base, pe1), new RolePermissionEntity(base, pe2)));

        // when clear first level cache
        entityManager.clear();

        long beforeRole, afterRole, afterPermissions;

        // First guess was sessionFactory.getStatistics().getQueryExecutionCount(), but it does not work as I expected
        beforeRole = sessionFactory.getStatistics().getPrepareStatementCount();
        RoleEntity subject = this.roleRepository.findByCode(base.getCode(), true).orElseThrow();
        //RoleEntity subject = this.roleRepository.findOne(Specification.where(RoleRepository.byCode(base.getCode())).and(RoleRepository.withPermissions())).orElseThrow();
        afterRole = sessionFactory.getStatistics().getPrepareStatementCount();
        assertEquals(base.getCode(), subject.getCode());
        assertEquals(beforeRole + 1, afterRole);

        // do something with permission or it won't get fetched
        int permissionsSize = subject.getRolePermission().stream()
            .map(RolePermissionEntity::getPermission).collect(Collectors.toSet()).size();
        assertEquals(2, permissionsSize);

        afterPermissions = sessionFactory.getStatistics().getPrepareStatementCount();
        assertEquals(afterRole, afterPermissions);
    }
}
