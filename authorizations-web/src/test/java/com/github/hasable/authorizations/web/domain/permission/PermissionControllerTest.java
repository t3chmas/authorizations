package com.github.hasable.authorizations.web.domain.permission;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hasable.authorizations.core.permission.Permission;
import com.github.hasable.authorizations.core.permission.PermissionService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PermissionController.class)
@Import({PermissionModelAssembler.class, PermissionMapperImpl.class})
public class PermissionControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private PermissionService permissionService;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void testGet200() throws Exception {
    Permission p = Permission.builder().code("OK").description("An OK permission").build();
    when(this.permissionService.findByCode(p.getCode())).thenReturn(Optional.of(p));
    String jsonResponse =
        this.mockMvc
            .perform(get("/permissions/" + p.getCode()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("self")))
            .andReturn()
            .getResponse()
            .getContentAsString();

    PermissionDTO result = objectMapper.readValue(jsonResponse, PermissionDTO.class);
    assertEquals(p.getCode(), result.getCode());
    assertEquals(p.getDescription(), result.getDescription());

    //    Traverson traverson =
    //        new Traverson(URI.create("http://localhost/permission/OK"), MediaTypes.HAL_JSON);
    //    PermissionDTO result = traverson.follow("self").toObject(PermissionDTO.class);
  }

  @Test
  void testGet404() throws Exception {
    when(this.permissionService.findByCode(anyString())).thenReturn(Optional.empty());
    this.mockMvc
        .perform(get("/permissions/KO"))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("path")))
        .andExpect(content().string(containsString("message")))
        .andExpect(content().string(containsString("error")));
  }

  void testNavigation() throws Exception {}
}
