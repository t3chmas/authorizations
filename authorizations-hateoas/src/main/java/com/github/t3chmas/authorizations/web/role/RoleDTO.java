package com.github.t3chmas.authorizations.web.role;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class RoleDTO {

    @EqualsAndHashCode.Include
    private String code;

    @EqualsAndHashCode.Include
    private String description;

    private Set<String> permissions;

}
