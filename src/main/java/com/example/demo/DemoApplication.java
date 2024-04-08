package com.example.demo;

import com.example.demo.entity.Role;
import com.example.demo.entity.enums.Roles;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	private final RoleService roleService;

	@Autowired
	public DemoApplication(RoleService roleService){
		this.roleService = roleService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(roleService.getAllRoles().isEmpty()){
			roleService.saveRole(Roles.ADMIN);
			roleService.saveRole(Roles.CUSTOMER);
		}
	}
}
