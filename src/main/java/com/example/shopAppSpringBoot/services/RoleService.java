package com.example.shopAppSpringBoot.services;

import com.example.shopAppSpringBoot.models.Role;
import com.example.shopAppSpringBoot.reposotories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
}

