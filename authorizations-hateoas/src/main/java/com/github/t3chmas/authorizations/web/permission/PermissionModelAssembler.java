package com.github.t3chmas.authorizations.web.permission;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PermissionModelAssembler
    implements RepresentationModelAssembler<PermissionDTO, EntityModel<PermissionDTO>> {

    private final int defaultPageSize;

    public PermissionModelAssembler(@Value("${application.web.default-page-size:10}") int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    @Override
    public @NonNull EntityModel<PermissionDTO> toModel(@NonNull PermissionDTO permission) {
        return EntityModel.of(
            permission,
            linkTo(methodOn(PermissionController.class).getAll(PageRequest.of(0, defaultPageSize))).withRel("all"),
            linkTo(methodOn(PermissionController.class).get(permission.getCode())).withSelfRel()
                .andAffordance(afford(methodOn(PermissionController.class).get(null)))
        );
    }
}
