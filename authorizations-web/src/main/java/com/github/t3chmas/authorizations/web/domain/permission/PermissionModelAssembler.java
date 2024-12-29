package com.github.hasable.authorizations.web.domain.permission;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PermissionModelAssembler
    implements RepresentationModelAssembler<PermissionDTO, EntityModel<PermissionDTO>> {

    @Override
    public @NonNull EntityModel<PermissionDTO> toModel(@NonNull PermissionDTO permission) {
        return EntityModel.of(
            permission,
            linkTo(methodOn(PermissionController.class).get(permission.getCode())).withSelfRel(),
            linkTo(methodOn(PermissionController.class).get(null)).withRel("get"),
            linkTo(methodOn(PermissionController.class).getAll(Pageable.ofSize(10))).withRel("all"));
    }
}
