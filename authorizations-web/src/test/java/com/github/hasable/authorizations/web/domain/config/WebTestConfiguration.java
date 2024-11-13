package com.github.hasable.authorizations.web.domain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebTestConfiguration implements WebMvcConfigurer {

//  @Bean
//  public PagedResourcesAssembler<PermissionDTO> pagedResourcesAssembler() {
//    return new PagedResourcesAssembler<>(
//        new HateoasPageableHandlerMethodArgumentResolver(),
//        UriComponentsBuilder.newInstance().scheme("http").host("localhost").build());
//  }
}
