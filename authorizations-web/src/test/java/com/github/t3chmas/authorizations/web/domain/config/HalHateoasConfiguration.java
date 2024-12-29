package com.github.hasable.authorizations.web.domain.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HalHateoasConfiguration implements WebMvcConfigurer {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeJackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.modulesToInstall(new Jackson2HalModule());
    }
}
