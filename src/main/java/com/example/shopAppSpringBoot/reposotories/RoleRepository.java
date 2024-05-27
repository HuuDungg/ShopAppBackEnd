package com.example.shopAppSpringBoot.reposotories;

import com.example.shopAppSpringBoot.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
