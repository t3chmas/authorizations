package com.github.t3chmas.authorizations.web;

import com.github.t3chmas.authorizations.web.permission.PermissionController;
import com.github.t3chmas.authorizations.web.role.RoleController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public RepresentationModel<?> index() {
        return new RepresentationModel<>()
            .add(PermissionController.ALL_PERMISSIONS_LINK)
            .add(RoleController.ALL_ROLES_LINK);
    }
}
