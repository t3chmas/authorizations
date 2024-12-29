package com.github.t3chmas.authorizations.storage.role;

import com.github.t3chmas.authorizations.storage.common.Traceable;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity(name = "role")
@NamedEntityGraph(name = "RoleEntity.withPermissions", attributeNodes = {
    @NamedAttributeNode("rolePermission")
}, subgraphs = {
    @NamedSubgraph(
        name = "rolePermissions",
        attributeNodes = {
            @NamedAttributeNode("permission")
        }
    )
}) // https://www.baeldung.com/jpa-entity-graph
public class RoleEntity extends Traceable {

    /**
     * Primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    @SequenceGenerator(name = "role_id_seq", allocationSize = 1)
    private Long id;

    @Nonnull
    @EqualsAndHashCode.Include
    private String code;

    @Lob
    private String description;

    @OneToMany(mappedBy = "role")
    private Set<RolePermissionEntity> rolePermission;

}
