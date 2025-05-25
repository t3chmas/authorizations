package com.github.t3chmas.authorizations.storage.role;

import com.github.t3chmas.authorizations.core.permission.Permission;
import com.github.t3chmas.authorizations.core.role.Role;
import com.github.t3chmas.authorizations.core.role.RolePort;
import com.github.t3chmas.authorizations.storage.permission.PermissionEntity;
import com.github.t3chmas.authorizations.storage.permission.PermissionRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RoleAdapter implements RolePort {

    private final Logger logger = LoggerFactory.getLogger(RoleAdapter.class);

    PermissionRepository pRepository;

    RoleRepository repository;

    RolePermissionRepository rpRepository;

    RoleMapper mapper;

    EntityManager entityManager;

    @Override
    public Set<Role> findAll() {
        return this.repository.findAll().stream()
            .map(mapper::roleEntityToRole)
            .collect(Collectors.toSet());
    }

    @Override
    public Page<Role> findAll(Pageable p) {
        return this.repository.findAll(p)
            .map(mapper::roleEntityToRole);
    }

    @Override
    public Optional<Role> findByCode(String code, boolean includePermissions) {
        return this.repository.findByCode(code, includePermissions).map(re -> mapper.roleEntityToRole(re, includePermissions));
    }

    @Override
    @Transactional
    public Role save(Role role) {

        // Get corresponding RoleEntity from database if possible, else creates a role Entity using only the mapper
        RoleEntity roleEntity = this.repository.findByCode(role.getCode(), role.getPermissions() != null)
            .map(re -> this.mapper.roleToRoleEntity(role, re))
            .orElseGet(() -> this.mapper.roleToRoleEntity(role));

        // when created from mapper, no id is present
        boolean creation = roleEntity.getId() == null;

        roleEntity = this.repository.save(roleEntity);
        logger.info("{} RoleEntity->{}", creation ? "Created" : "Updated", roleEntity);

        if (role.getPermissions() != null) {
            // stores to Map for direct access
            Map<String, RolePermissionEntity> existingPermissions = Optional.ofNullable(roleEntity.getRolePermission())
                .orElseGet(Collections::emptySet)
                .stream()
                .collect(Collectors.toMap(rpe -> rpe.getPermission().getCode(), rpe -> rpe));

            // get a list of wanted permissions
            Set<String> newPermissions = new TreeSet<>();
            for (Permission p : role.getPermissions()) {
                newPermissions.add(p.getCode());
                if (!existingPermissions.containsKey(p.getCode())) {
                    PermissionEntity pe = pRepository
                        .findByCode(p.getCode())
                        .orElseThrow(() -> new IllegalArgumentException("Unknown Permission with code <" + p.getCode() + ">"));
                    // that's a new one
                    rpRepository.save(RolePermissionEntity.builder()
                        .role(roleEntity)
                        .permission(pe)
                        .build());
                    logger.info("Added Permission to RoleEntity(id:{})->{}", roleEntity.getId(), pe);
                }
            }

            // remove permissions not in list of wanted permissions
            for (RolePermissionEntity rpe : existingPermissions.values()) {
                if (!newPermissions.contains(rpe.getPermission().getCode())) {
                    this.rpRepository.deleteById(rpe.getId());
                    logger.info("Removed Permission from RoleEntity(id:{})->{}", roleEntity.getId(), rpe.getPermission());
                }
            }
        }
        entityManager.flush();
        // needed because we use Criteria query...
        entityManager.clear();
        return this.findByCode(role.getCode(), role.getPermissions() != null).orElseThrow();
    }

    @Override
    @Transactional
    public void remove(String role) {
        // Get corresponding RoleEntity from database if possible, else throw an exception
        RoleEntity roleEntity = this.repository.findByCode(role, false)
            .orElseThrow(() -> new IllegalArgumentException("Cannot remove role <" + role + "> from database"));

        long count = this.rpRepository.deleteByRoleId(roleEntity.getId());
        this.repository.delete(roleEntity);
        logger.info("Removed RoleEntity->{}", roleEntity);
        logger.debug("Removed {} links to Permissions", count);
    }
}
