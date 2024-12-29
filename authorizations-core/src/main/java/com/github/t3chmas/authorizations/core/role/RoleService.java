package com.github.t3chmas.authorizations.core.role;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@Service
public class RoleService {

    private final Logger logger = LoggerFactory.getLogger(RolePort.class);

    private RolePort repository;

    public Set<Role> findAll() {
        logger.trace("Finding all roles...");
        Set<Role> roles = this.repository.findAll();
        logger.trace("Found {} roles!", roles.size());
        return roles;
    }
}
