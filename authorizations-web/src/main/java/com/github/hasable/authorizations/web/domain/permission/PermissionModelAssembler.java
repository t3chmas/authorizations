package com.github.hasable.authorizations.web.domain.permission;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PermissionModelAssembler
    implements RepresentationModelAssembler<PermissionDTO, EntityModel<PermissionDTO>> {

  @Override
  public @NonNull EntityModel<PermissionDTO> toModel(@NonNull PermissionDTO permission) {
    return EntityModel.of(
        permission,
        linkTo(methodOn(PermissionController.class).get(permission.getCode(), false)).withSelfRel(),
        linkTo(methodOn(PermissionController.class).get(null, false)).withRel("get"),
        linkTo(methodOn(PermissionController.class).getAll(Pageable.ofSize(10))).withRel("all"));
  }
}
