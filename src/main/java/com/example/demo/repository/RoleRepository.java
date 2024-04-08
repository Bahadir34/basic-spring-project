package com.example.demo.repository;

import com.example.demo.entity.Role;
import com.example.demo.entity.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role , Long> {


    @Query(value = "SELECT (COUNT(r) > 0) FROM Role r WHERE r.role = ?1")
    boolean existByRoles(Roles roles);

    Role findByRoleEquals(Roles roles);
}
