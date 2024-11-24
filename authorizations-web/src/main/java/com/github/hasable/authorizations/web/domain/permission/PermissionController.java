package com.github.hasable.authorizations.web.domain.permission;

import com.github.hasable.authorizations.core.permission.PermissionService;
import com.github.hasable.authorizations.web.domain.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/permissions")
public class PermissionController {

    public static final Link ALL_PERMISSIONS_LINK;

    public static final Link READ_PERMISSION_LINK;

    static {
        ALL_PERMISSIONS_LINK = linkTo(methodOn(PermissionController.class).getAll(Pageable.ofSize(10))).withRel("permissions");
        READ_PERMISSION_LINK = linkTo(methodOn(PermissionController.class).get(null)).withRel("permission");
    }

    private final PagedResourcesAssembler<PermissionDTO> pagedResourcesAssembler;

    private final PermissionModelAssembler permissionModelAssembler;

    private final PermissionService permissionService;

    private final PermissionMapper permissionMapper;

    @Autowired
    public PermissionController(
        PagedResourcesAssembler<PermissionDTO> pagedResourcesAssembler,
        PermissionModelAssembler permissionModelAssembler,
        PermissionService permissionService,
        PermissionMapper permissionMapper) {

        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.permissionModelAssembler = permissionModelAssembler;
        this.permissionService = permissionService;
        this.permissionMapper = permissionMapper;
    }

    @GetMapping
    ResponseEntity<PagedModel<EntityModel<PermissionDTO>>> getAll(Pageable pageable) {

        Page<PermissionDTO> permissions = this.permissionService.findAll(pageable).map(permissionMapper::permissionToPermissionDTO);
        PagedModel<EntityModel<PermissionDTO>> page = pagedResourcesAssembler.toModel(permissions, this.permissionModelAssembler);
        page.add(ALL_PERMISSIONS_LINK, READ_PERMISSION_LINK);
        return ResponseEntity.status(permissions.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(page);
    }

    @GetMapping("/{permissionCode}")
    ResponseEntity<EntityModel<PermissionDTO>> get(@PathVariable String permissionCode) {

        return ResponseEntity.ok(
            permissionModelAssembler.toModel(
                this.permissionService
                    .findByCode(permissionCode)
                    .map(this.permissionMapper::permissionToPermissionDTO)
                    .orElseThrow(
                        () ->
                            new ResourceNotFoundException(
                                "Permission with code " + permissionCode + " not found"))));
    }
}
