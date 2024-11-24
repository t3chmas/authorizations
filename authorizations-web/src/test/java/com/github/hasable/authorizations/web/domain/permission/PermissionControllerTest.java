package com.github.hasable.authorizations.web.domain.permission;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hasable.authorizations.core.permission.Permission;
import com.github.hasable.authorizations.core.permission.PermissionService;
import com.github.hasable.authorizations.web.domain.config.HalHateoasConfiguration;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PermissionController.class)
@Import({HalHateoasConfiguration.class, PermissionModelAssembler.class, PermissionMapperImpl.class})
public class PermissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PermissionService permissionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAll200() throws Exception {

        // given
        List<Permission> permissions = randomPermissions(8);

        // when
        when(this.permissionService.findAll(any())).thenAnswer(
            answer -> new PageImpl<>(permissions, answer.getArgument(0), permissions.size())
        );

        // then
        String jsonResponse =
            this.mockMvc
                .perform(get(PermissionController.ALL_PERMISSIONS_LINK.toUri()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PagedModel<EntityModel<PermissionDTO>> pPagedModel = objectMapper.readValue(jsonResponse, new TypeReference<PagedModel<EntityModel<PermissionDTO>>>() {});

        Objects.requireNonNull(pPagedModel.getMetadata());
        assertEquals(0, pPagedModel.getMetadata().getNumber());
        assertEquals(1, pPagedModel.getMetadata().getTotalPages());
        assertEquals(permissions.size(), pPagedModel.getMetadata().getTotalElements());

        Objects.requireNonNull(pPagedModel.getContent());
        assertEquals(permissions.size(), pPagedModel.getContent().size());

        Objects.requireNonNull(pPagedModel.getLinks());
        assertNotNull(pPagedModel.getLink(IanaLinkRelations.SELF));
        assertNotNull(pPagedModel.getLink(PermissionController.ALL_PERMISSIONS_LINK.getRel()));
        assertNotNull(pPagedModel.getLink(PermissionController.READ_PERMISSION_LINK.getRel()));
    }

    @Test
    void testAll204() throws Exception {

        // given
        List<Permission> permissions = randomPermissions(8);

        // when
        when(this.permissionService.findAll(any())).thenAnswer(
            answer -> Page.empty(answer.getArgument(0))
        );

        // then
        String jsonResponse =
            this.mockMvc
                .perform(get(PermissionController.ALL_PERMISSIONS_LINK.toUri()))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PagedModel<EntityModel<PermissionDTO>> pPagedModel = objectMapper.readValue(jsonResponse, new TypeReference<PagedModel<EntityModel<PermissionDTO>>>() {});

        Objects.requireNonNull(pPagedModel.getMetadata());
        assertEquals(0, pPagedModel.getMetadata().getNumber());
        assertEquals(0, pPagedModel.getMetadata().getTotalPages());
        assertEquals(0, pPagedModel.getMetadata().getTotalElements());

        assertTrue(pPagedModel.getContent().isEmpty());

        Objects.requireNonNull(pPagedModel.getLinks());
        assertNotNull(pPagedModel.getLink(IanaLinkRelations.SELF));
        assertNotNull(pPagedModel.getLink(PermissionController.ALL_PERMISSIONS_LINK.getRel()));
        assertNotNull(pPagedModel.getLink(PermissionController.READ_PERMISSION_LINK.getRel()));
    }

    @Test
    void testRead200() throws Exception {

        // given
        Permission p = randomPermission();

        // when
        when(this.permissionService.findByCode(p.getCode())).thenReturn(Optional.of(p));

        // then
        String jsonResponse =
            this.mockMvc
                .perform(get(PermissionController.READ_PERMISSION_LINK.expand(p.getCode()).toUri()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("self")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        EntityModel<PermissionDTO> rModel = objectMapper.readValue(jsonResponse, new TypeReference<EntityModel<PermissionDTO>>() {});
        assertEquals(p.getCode(), Objects.requireNonNull(rModel.getContent()).getCode());
        assertEquals(p.getDescription(), Objects.requireNonNull(rModel.getContent()).getDescription());

        PermissionDTO result = objectMapper.readValue(jsonResponse, PermissionDTO.class);
        assertEquals(p.getCode(), result.getCode());
        assertEquals(p.getDescription(), result.getDescription());
    }

    @Test
    void testRead404() throws Exception {

        // given
        Permission p = randomPermission();

        // when
        when(this.permissionService.findByCode(anyString())).thenReturn(Optional.empty());

        // then
        this.mockMvc
            .perform(get(PermissionController.READ_PERMISSION_LINK.expand(p.getCode()).toUri()))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().string(containsString("path")))
            .andExpect(content().string(containsString("message")))
            .andExpect(content().string(containsString("error")));
    }

    void testNavigation() throws Exception {
    }

    public Permission randomPermission() {
        return Permission.builder().code(RandomStringUtils.randomAlphanumeric(32)).description(RandomStringUtils.randomAlphanumeric(128)).build();
    }

    public List<Permission> randomPermissions(int count) {
        return IntStream.range(0, count)
            .mapToObj(i -> randomPermission())
            .collect(Collectors.toList());
    }
}
