package com.github.t3chmas.authorizations.storage.role;

import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>, JpaSpecificationExecutor<RoleEntity> {

    static Specification<RoleEntity> byCode(String code) {
        return (role, cq, cb) -> cb.equal(role.get(RoleEntity_.code), code);
    }

    static Specification<RoleEntity> withPermissions() {
        return (role, cq, cb) -> {
            role.fetch(RoleEntity_.ROLE_PERMISSION, JoinType.LEFT).fetch(RolePermissionEntity_.PERMISSION, JoinType.LEFT);
            return cb.isTrue(cb.literal(true));
        };
    }

    /**
     * Find a role by its code
     *
     * @param code the searched code
     * @return the role if found
     */
    Optional<RoleEntity> findByCode(String code);

    default Optional<RoleEntity> findByCode(String code, boolean withPermissions) {
        if (!withPermissions)
            return findByCode(code);
        return findOne(Specification.where(byCode(code)).and(withPermissions()));
    }
}
