package com.github.t3chmas.authorizations.storage.role;

import com.github.t3chmas.authorizations.core.role.Role;
import com.github.t3chmas.authorizations.core.role.RolePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RoleAdapter implements RolePort {

    RoleRepository repository;

    RoleMapper mapper;

    @Autowired
    public RoleAdapter(RoleRepository repository, RoleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Set<Role> findAll() {
        return Set.of();
    }

    @Override
    public Optional<Role> findByCode(String code, boolean includePermissions) {
        return this.repository.findByCode(code, includePermissions).map(re -> mapper.roleEntityToRole(re, includePermissions));
    }

    @Override
    public void store(Role role) {

    }
}
