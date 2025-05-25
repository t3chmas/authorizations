package com.github.t3chmas.authorizations.web.role;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RoleModelAssembler
    implements RepresentationModelAssembler<RoleDTO, EntityModel<RoleDTO>> {

    @Override
    public @NonNull EntityModel<RoleDTO> toModel(@NonNull RoleDTO role) {
        return EntityModel.of(
            role,
            linkTo(methodOn(RoleController.class).get(role.getCode())).withSelfRel()
                .andAffordance(afford(methodOn(RoleController.class).delete(role.getCode())))
                .andAffordance(afford(methodOn(RoleController.class).put(role.getCode(), role)))
        );
    }
}