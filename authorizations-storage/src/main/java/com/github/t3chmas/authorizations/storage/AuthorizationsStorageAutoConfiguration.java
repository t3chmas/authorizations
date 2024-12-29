package com.github.t3chmas.authorizations.storage;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@ComponentScan
@EnableJpaRepositories
@EntityScan
public class AuthorizationsStorageAutoConfiguration {}
