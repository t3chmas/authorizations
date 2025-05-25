package com.github.t3chmas.authorizations.web.role;

import com.github.t3chmas.authorizations.core.role.RoleService;
import com.github.t3chmas.authorizations.web.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    public static final Link ALL_ROLES_LINK;

    public static final Link DEL_ROLE_LINK;

    public static final Link PUT_ROLE_LINK;

    public static final Link READ_ROLE_LINK;

    static {
        ALL_ROLES_LINK = linkTo(methodOn(RoleController.class).getAll(Pageable.ofSize(10))).withRel("roles");
        READ_ROLE_LINK = linkTo(methodOn(RoleController.class).get(null)).withRel("role");
        PUT_ROLE_LINK = linkTo(methodOn(RoleController.class).put(null, null)).withRel("create");
        DEL_ROLE_LINK = linkTo(methodOn(RoleController.class).delete(null)).withRel("delete");
    }

    private final PagedResourcesAssembler<RoleDTO> pagedResourcesAssembler;

    private final RoleModelAssembler roleModelAssembler;

    private final RoleService roleService;

    private final RoleMapper roleMapper;

    public RoleController(PagedResourcesAssembler<RoleDTO> pagedResourcesAssembler, RoleModelAssembler roleModelAssembler, RoleService roleService, RoleMapper roleMapper) {
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.roleModelAssembler = roleModelAssembler;
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @DeleteMapping("/{roleCode}")
    ResponseEntity<RepresentationModel<?>> delete(@PathVariable String roleCode) {
        this.roleService.remove(roleCode);
        return ResponseEntity.ok(
            new RepresentationModel<>()
                .add(RoleController.ALL_ROLES_LINK)
        );
    }

    @GetMapping
    ResponseEntity<PagedModel<EntityModel<RoleDTO>>> getAll(Pageable pageable) {
        Page<RoleDTO> roles = this.roleService.findAll(pageable).map(roleMapper::toDTO);
        PagedModel<EntityModel<RoleDTO>> page = pagedResourcesAssembler.toModel(roles, this.roleModelAssembler);
        page.add(ALL_ROLES_LINK, DEL_ROLE_LINK, PUT_ROLE_LINK, READ_ROLE_LINK);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/{roleCode}")
    ResponseEntity<EntityModel<RoleDTO>> get(@PathVariable String roleCode) {
        return ResponseEntity.ok(
            roleModelAssembler.toModel(
                this.roleService
                    .findByCode(roleCode)
                    .map(this.roleMapper::toDTO)
                    .orElseThrow(
                        () ->
                            new ResourceNotFoundException(
                                "Role with code " + roleCode + " not found"))));
    }

    @PutMapping(path = "/{roleCode}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<RoleDTO>> put(@PathVariable String roleCode, @RequestBody(required = false) RoleDTO role) {
        RoleDTO r = (role != null ? role : new RoleDTO());
        r.setCode(roleCode);

        return ResponseEntity.ok(
            this.roleModelAssembler.toModel(
                this.roleMapper.toDTO(
                    this.roleService.store(
                        this.roleMapper.fromDTO(r))
                )
            )
        );
    }

}
