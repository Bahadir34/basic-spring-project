package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.enums.Roles;
import com.example.demo.exception.exceptions.ConflictException;
import com.example.demo.messages.ExceptionMessages;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }


    public List<Role> getAllRoles() {

    return roleRepository.findAll();
    }

    public void saveRole(Roles roles) {
        if(roleRepository.existByRoles(roles)){
            throw new ConflictException(ExceptionMessages.CONFLICT_ROLE_ALREADY_SAVED);
        }

        Role role = Role.builder().role(roles).build();
        roleRepository.save(role);
    }

    public Role findRoleEquels(Roles roles) {
        return roleRepository.findByRoleEquals(roles);
    }
}
