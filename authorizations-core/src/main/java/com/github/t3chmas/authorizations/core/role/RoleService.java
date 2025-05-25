package com.github.t3chmas.authorizations.core.role;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class RoleService {

    private final Logger logger = LoggerFactory.getLogger(RoleService.class);

    private RolePort repository;

    public Set<Role> findAll() {
        logger.trace("Finding all roles...");
        Set<Role> roles = this.repository.findAll();
        logger.trace("Found {} roles!", roles.size());
        return roles;
    }

    public Page<Role> findAll(Pageable p) {
        logger.trace("Finding roles from page {}", p.toString());
        Page<Role> roles = this.repository.findAll(p);
        logger.trace("Found {} roles!", roles.getContent().size());
        return roles;
    }

    /**
     * Find a permission by its code
     *
     * @return The permission, if found
     */
    public Optional<Role> findByCode(String code) {
        if (!StringUtils.hasText(code)) throw new IllegalArgumentException("<code> must not be blank");
        logger.trace("Looking for role with code={}", code);
        Optional<Role> role = this.repository.findByCode(code);
        if (role.isPresent()) logger.trace("Found role {}", role);
        else logger.trace("Found nothing");
        return role;
    }

    public Role store(Role role) {
        if (role == null) throw new IllegalArgumentException("<role> must not be blank");
        return this.repository.save(role);
    }

    public void remove(String role) {
        this.repository.remove(role);
    }

    public void remove(Role role) {
        this.remove(role.getCode());
    }
}
