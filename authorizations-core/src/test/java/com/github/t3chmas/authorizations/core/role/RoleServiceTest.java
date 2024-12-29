package com.github.t3chmas.authorizations.core.role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    RolePort roleRepository;

    @Autowired
    @InjectMocks
    RoleService roleService;

    @Test
    public void test_findAllEmpty() {

        // Given empty list
        Set<Role> result = new HashSet<Role>();

        // when
        when(this.roleRepository.findAll()).thenReturn(result);

        // then
        assertNotNull(this.roleService.findAll());
        assertEquals(0, this.roleService.findAll().size());
    }

    @Test
    public void test_findAllNotEmpty() {

        // Given two roles list
        Set<Role> result = Set.of(RoleTest.random(), RoleTest.random());

        // when
        when(this.roleRepository.findAll()).thenReturn(result);

        // then
        assertNotNull(this.roleService.findAll());
        assertEquals(2, this.roleService.findAll().size());
    }
}
