package com.github.hasable.authorizations.web.domain;

import com.github.hasable.authorizations.web.domain.permission.PermissionController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public RepresentationModel<?> index() {
        RepresentationModel<?> index = new RepresentationModel<>();
        index.add(PermissionController.ALL_PERMISSIONS_LINK);
        return index;
    }
}
