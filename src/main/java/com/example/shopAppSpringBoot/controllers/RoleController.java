package com.example.shopAppSpringBoot.controllers;

import com.example.shopAppSpringBoot.exceptions.DataNotFoundException;
import com.example.shopAppSpringBoot.models.Role;
import com.example.shopAppSpringBoot.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/getAllRoles")
    public ResponseEntity<?> getAllRole() throws DataNotFoundException {

        return ResponseEntity.ok().body(roleService.getAllRole());
    }
}
