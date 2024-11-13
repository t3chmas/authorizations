package com.github.hasable.authorizations.web.domain.permission;

import com.github.hasable.authorizations.core.permission.PermissionService;
import com.github.hasable.authorizations.web.domain.common.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

  private final PagedResourcesAssembler<PermissionDTO> pagedResourcesAssembler;
  private final PermissionModelAssembler permissionModelAssembler;
  private final List<PermissionDTO> permissions;
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

    permissions = new ArrayList<>(128);
    for (int i = 0; i < 128; i++) {
      permissions.add(
          new PermissionDTO(
              RandomStringUtils.randomAlphanumeric(128),
              RandomStringUtils.randomAlphanumeric(1024)));
    }
  }

  @GetMapping
  ResponseEntity<PagedModel<EntityModel<PermissionDTO>>> getAll(Pageable pageable) {

    int pageSize = pageable.getPageSize();
    int currentPage = pageable.getPageNumber();
    int startItem = currentPage * pageSize;

    List<PermissionDTO> listPage;

    if (permissions.size() < startItem) {
      listPage = List.of(); // return an empty list if the start index is greater than the list size
    } else {
      int toIndex = Math.min(startItem + pageSize, permissions.size());
      listPage = permissions.subList(startItem, toIndex);
    }

    // page<R>
    Page<PermissionDTO> page = new PageImpl<>(listPage, pageable, permissions.size());
    RepresentationModelAssembler<PermissionDTO, EntityModel<PermissionDTO>> rma =
        this.permissionModelAssembler;

    return ResponseEntity.ok(pagedResourcesAssembler.toModel(page, this.permissionModelAssembler));
  }

  @GetMapping("/{permissionCode}")
  ResponseEntity<EntityModel<PermissionDTO>> get(
      @PathVariable String permissionCode,
      @RequestParam(required = false, defaultValue = "false") boolean eager) {

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
